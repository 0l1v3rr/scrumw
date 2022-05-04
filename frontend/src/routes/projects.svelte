<script context="module">
    export async function load({ fetch, session }) {
        const userRes = await fetch(`http://localhost:8080/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`http://localhost:8080/api/v1/projects/${user.username}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const projects = await res.json();

        if(res.ok) {
            return {
                props: { projects }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import ProjectCard from "../components/cards/ProjectCard.svelte";
    import NotFound from "../components/cards/NotFound.svelte";

    export let projects;
    let currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));

    let selectValue;

    const handleFilterOptionsChange = () => {
        if(selectValue == "latest-created") {
            currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
            return;
        }

        if(selectValue == "earliest-created") {
            currentProjects = [...projects].sort((a,b) => (a.created > b.created) ? 1 : ((b.created > a.created) ? -1 : 0));
            return;
        }

        if(selectValue == "project") {
            currentProjects = [...projects].sort((a,b) => (a.projectName > b.projectName) ? 1 : ((b.projectName > a.projectName) ? -1 : 0));
            return;
        }

        if(selectValue == "user") {
            currentProjects = [...projects].sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0));
            return;
        }

        if(selectValue == "public") {
            currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
            currentProjects = currentProjects.filter(p => p.isPublic);
            return;
        }

        if(selectValue == "private") {
            currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
            currentProjects = currentProjects.filter(p => !p.isPublic);
            return;
        }
    };
</script>

<svelte:head>
    <title>Projects | scrumw</title>
</svelte:head>

<main>
    <div class="projects-header">
        <div class="projects-title">Projects</div>
        <form class="header-form">
            <div class="select">
                <label for="filter-by">Filter By: </label>
                <select bind:value={selectValue} on:change={handleFilterOptionsChange} id="filter-by">
                    <option value="latest-created" selected>Latest Created</option>
                    <option value="earliest-created">Earliest Created</option>
                    <option value="project">Project name</option>
                    <option value="user">User</option>
                    <option value="public">Public</option>
                    <option value="private">Private</option>
                </select>
            </div>
        </form>
    </div>

    <div class="projects-container">
        {#if currentProjects.length == 0}
            <NotFound searchQuery="project" />
        {:else}
            {#each currentProjects as project}
                <ProjectCard 
                    owner={project.username}
                    name={project.projectName}
                    description={project.projectDescription}
                    isPublic={project.isPublic}
                    created={project.created}
                />
            {/each}
        {/if}
    </div>
</main>

<style>
    .projects-header {
        padding: 0 1rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .projects-title {
        color: var(--text-color-primary);
        font-weight: bold;
        font-size: 1.1rem;
    }
    .header-form {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 1.2rem;
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .5rem;
        background-color: var(--background-primary);
    }
    .select {
        display: flex;
        gap: .5rem;
        align-items: center;
    }
    .select > label {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    #filter-by {
        padding: .25rem .5rem;
        background-color: var(--background-primary);
        color: var(--text-color-secondary);
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
    }
    #filter-by:focus {
        border-color: var(--text-color-secondary);
    }
    .projects-header {
        padding: 0 1rem;
    }
    .projects-container {
        background-color: var(--background-secondary);
        padding: 1rem;
        gap: 1rem;
        height: 100%;
        display: grid;
        grid-template-columns: repeat(2, 1fr);
    }
    @media screen and (max-width: 768px) {
        .projects-container {
            grid-template-columns: 1fr;
        }
    }
</style>