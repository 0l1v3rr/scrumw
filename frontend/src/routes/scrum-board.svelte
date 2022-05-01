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

        return {
            props: { 
                projects
            }
        };
    }
</script>

<script>
    import ScrumCard from "../components/cards/ScrumCard.svelte";

    export let projects;
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
                <select id="select-project">
                    <option value="all" selected>All</option>
                    {#each projects as project}
                        <option value={project.username+"/"+project.projectName}>{project.username}/{project.projectName}</option>
                    {/each}
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
    </div>

    <div class="scrums-container">
        
        <div class="scrum-section">
            <div class="scrum-section-header tasks">
                <div class="header-title">Tasks</div>
                <div class="header-count">2</div>
            </div>

            <ScrumCard 
                project="0l1v3rr/test"
                description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam, cumque!"
                title="Test scrum"
                assignedBy="j0hn"
            />

        </div>

        <div class="scrum-section in-progress-section">
            <div class="scrum-section-header in-progress">
                <div class="header-title">In Progress</div>
                <div class="header-count">2</div>
            </div>

            <ScrumCard 
                project="0l1v3rr/test"
                description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam, cumque!"
                title="Test scrum"
                assignedBy="j0hn"
            />
        </div>

        <div class="scrum-section">
            <div class="scrum-section-header done">
                <div class="header-title">Done</div>
                <div class="header-count">2</div>
            </div>

            <ScrumCard 
                project="0l1v3rr/test"
                description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam, cumque!"
                title="Test scrum"
                assignedBy="fr3dd1e"
                approvedBy="thomas"
            />
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