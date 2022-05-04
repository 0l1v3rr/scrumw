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

    export let projects;
</script>

<svelte:head>
    <title>Projects | scrumw</title>
</svelte:head>

<main>
    <div class="projects-header">
        <div class="project-title">Projects</div>
    </div>

    <div class="projects-container">
        {#each projects as project}
            <ProjectCard 
                owner={project.username}
                name={project.projectName}
                description={project.projectDescription}
                isPublic={project.isPublic}
                created={project.created}
            />
        {/each}
    </div>
</main>

<style>
    .projects-header {
        padding: 0 1rem;
    }
    .project-title {
        color: var(--text-color-primary);
        font-weight: bold;
        font-size: 1.1rem;
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