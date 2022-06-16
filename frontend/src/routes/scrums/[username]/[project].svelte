<script context="module">
    export async function load({ fetch, session, params }) {
        const res = await fetch(`${session.apiURL}/api/v1/scrum/${params.username}/${params.project}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });

        if(res.ok) {
            const scrum = await res.json();
            
            return {
                props: { 
                    scrums: scrum,
                }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import { page } from '$app/stores';
    const { username, project } = $page.params;

    import ScrumCard from "../../../components/cards/ScrumCard.svelte";
    import NotFound from "../../../components/cards/NotFound.svelte";
    import { FolderMinusIcon } from 'svelte-feather-icons';

    export let scrums;
</script>

<svelte:head>
    <title>Scrums - {username}/{project} | scrumw</title>
</svelte:head>

<main class="main">
    <div class="project-header">
        <FolderMinusIcon size="24" />
        <a href="/project/{username}/{project}" class="project-title">{username}<span class="text-muted">/</span>{project}</a>
    </div>

    <div class="scrums-container">
        
        <div class="scrum-section">
            <div class="scrum-section-header tasks">
                <div class="header-title">To Do</div>
                <div class="header-count">{scrums.filter(s => s.status.toLowerCase() === "to_do").length}</div>
            </div>

            {#if scrums.filter(s => s.status.toLowerCase() === "to_do").length === 0}
                <div style="margin-top: 1rem;">
                    <NotFound searchQuery="todo scrums" />
                </div>
            {:else}
                {#each scrums.filter(s => s.status.toLowerCase() === "to_do") as scrum}
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
                <div class="header-count">{scrums.filter(s => s.status.toLowerCase() === "in_progress").length}</div>
            </div>

            {#if scrums.filter(s => s.status.toLowerCase() === "in_progress").length === 0}
                <div style="margin-top: 1rem;">
                    <NotFound searchQuery="in progress scrums" />
                </div>
            {:else}
                {#each scrums.filter(s => s.status.toLowerCase() === "in_progress") as scrum}
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
                <div class="header-count">{scrums.filter(s => s.status.toLowerCase() === "done").length}</div>
            </div>

            {#if scrums.filter(s => s.status.toLowerCase() === "done").length === 0}
                <div style="margin-top: 1rem;">
                    <NotFound searchQuery="done scrums" />
                </div>
            {:else}
                {#each scrums.filter(s => s.status.toLowerCase() === "done") as scrum}
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
    .project-title {
        color: var(--link-color);
        font-size: 1.5rem;
        transition: .3s ease-in-out;
        font-weight: bold;
    }
    .project-header {
        display: flex;
        align-items: center;
        gap: .5rem;
        color: var(--text-color-secondary);
        width: fit-content;
        margin-left: 1.2rem;
        margin-bottom: .3rem;
        margin-top: .3rem;
    }
    .project-title:hover {
        color: var(--color-primary-light);
    }
    .text-muted {
        color: var(--text-color-secondary);
        margin: .25rem;
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