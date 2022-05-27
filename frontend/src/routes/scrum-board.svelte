<script context="module">
    export async function load({ fetch, session }) {
        const userRes = await fetch(`http://localhost:8080/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const scrumsRes = await fetch(`http://localhost:8080/api/v1/scrum/${user.username}`, {
            method: 'GET',
            headers: {
                "token": session.token
            }
        });
        const scrums = await scrumsRes.json();

        return {
            props: { 
                scrums: scrums
            }
        };
    }
</script>

<script>
    import ScrumCard from "../components/cards/ScrumCard.svelte";
    import NotFound from "../components/cards/NotFound.svelte";

    let projects = [];
    let projectStore = [];
    export let scrums;
    let currentScrums = [...scrums];

    let selectedValue;

    for(const i of scrums) {
        const project = {
            username: i.projectOwner,
            projectName: i.projectName
        };

        const currentProject = `${i.projectOwner}/${i.projectName}`;
        
        if(!projectStore.includes(currentProject)) {
            projectStore.push(currentProject);
            projects.push(project);
        }
    }

    const handleSelectChange = () => {
        if(selectedValue == "all") {
            currentScrums = [...scrums];
            return;
        }

        const splitted = selectedValue.split('/');
        currentScrums = [...scrums].filter(s => s.projectOwner === splitted[0] && s.projectName === splitted[1]);
    };
</script>

<svelte:head>
    <title>Scrum Board | scrumw</title>
</svelte:head>

<main class="main">
    <div class="dashboard-header">
        <div class="dashboard-title">Scrum Board</div>
        <form class="header-form">
            <div class="select">
                <label for="select-project">Project: </label>
                <select id="select-project" bind:value={selectedValue} on:change={handleSelectChange}>
                    <option value="all" selected>All</option>
                    {#each projects as project}
                        <option value={project.username+"/"+project.projectName}>{project.username}/{project.projectName}</option>
                    {/each}
                </select>
            </div>
        </form>
    </div>

    <div class="scrums-container">
        
        <div class="scrum-section">
            <div class="scrum-section-header tasks">
                <div class="header-title">To Do</div>
                <div class="header-count">{currentScrums.filter(s => s.status.toLowerCase() === "to_do").length}</div>
            </div>

            {#if currentScrums.filter(s => s.status.toLowerCase() === "to_do").length === 0}
                <div style="margin-top: 1rem;">
                    <NotFound searchQuery="todo scrums" />
                </div>
            {:else}
                {#each currentScrums.filter(s => s.status.toLowerCase() === "to_do") as scrum}
                    <ScrumCard 
                        id={scrum.id}
                        projectOwner={scrum.projectOwner}
                        projectName={scrum.projectName}
                        description={scrum.description}
                        title={scrum.title}
                        createdBy={scrum.createdBy}
                        updated={scrum.updated}
                    />
                {/each}
            {/if}
        </div>

        <div class="scrum-section in-progress-section">
            <div class="scrum-section-header in-progress">
                <div class="header-title">In Progress</div>
                <div class="header-count">{currentScrums.filter(s => s.status.toLowerCase() === "in_progress").length}</div>
            </div>

            {#if currentScrums.filter(s => s.status.toLowerCase() === "in_progress").length === 0}
                <div style="margin-top: 1rem;">
                    <NotFound searchQuery="in progress scrums" />
                </div>
            {:else}
                {#each currentScrums.filter(s => s.status.toLowerCase() === "in_progress") as scrum}
                    <ScrumCard 
                        id={scrum.id}
                        projectOwner={scrum.projectOwner}
                        projectName={scrum.projectName}
                        description={scrum.description}
                        title={scrum.title}
                        createdBy={scrum.createdBy}
                        updated={scrum.updated}
                    />
                {/each}
            {/if}
        </div>

        <div class="scrum-section">
            <div class="scrum-section-header done">
                <div class="header-title">Done</div>
                <div class="header-count">{currentScrums.filter(s => s.status.toLowerCase() === "done").length}</div>
            </div>

            {#if currentScrums.filter(s => s.status.toLowerCase() === "done").length === 0}
                <div style="margin-top: 1rem;">
                    <NotFound searchQuery="done scrums" />
                </div>
            {:else}
                {#each currentScrums.filter(s => s.status.toLowerCase() === "done") as scrum}
                    <ScrumCard 
                        id={scrum.id}
                        projectOwner={scrum.projectOwner}
                        projectName={scrum.projectName}
                        description={scrum.description}
                        title={scrum.title}
                        createdBy={scrum.createdBy}
                        updated={scrum.updated}
                    />
                {/each}
            {/if}
        </div>

    </div>
</main>

<style>
    .dashboard-header {
        padding: 0 1rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .dashboard-title {
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
    #select-project {
        padding: .25rem .5rem;
        background-color: var(--background-primary);
        color: var(--text-color-secondary);
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
    }
    #select-project:focus {
        border-color: var(--text-color-secondary);
    }
    .scrums-container {
        background-color: var(--background-secondary);
        display: flex;
        align-items: start;
        justify-content: center;
        padding: 1rem;
        gap: 1rem;
        height: 100%;
    }
    .scrum-section {
        width: 33%;
    }
    .scrum-section-header {
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: .5rem;
        width: fit-content;
        padding: .5rem 1rem;
        border: 1px solid var(--border-color);
    }
    .header-count {
        margin-left: .6rem;
        font-size: .9rem;
        border-radius: 50%;
        padding: .25rem .5rem;
    }
    .tasks > .header-title {
        color: var(--color-danger-light);
    }
    .tasks > .header-count {
        background-color: var(--color-danger);
    }
    .in-progress > .header-title {
        color: var(--color-warning-light);
    }
    .in-progress > .header-count {
        background-color: var(--color-warning);
        color: var(--background-primary);
    }
    .done > .header-title {
        color: var(--color-success-light);
    }
    .done > .header-count {
        background-color: var(--color-success);
    }

    @media screen and (max-width: 576px) {
        .in-progress-section {
            display: none;
        }
        .scrum-section {
            width: 50%;
        }
    }
</style>