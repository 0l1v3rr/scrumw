<script context="module">
    export async function load({ fetch, session, params }) {
        const userRes = await fetch(`${session.apiURL}/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`${session.apiURL}/api/v1/scrum/id/${params.id}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });

        if(res.ok) {
            const scrum = await res.json();
            return {
                props: { 
                    scrum: scrum, 
                    status: res.status,
                    user: user
                }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import { variables } from "../../lib/env.js";
    import { FolderMinusIcon } from 'svelte-feather-icons';
    import { page } from '$app/stores';
    const { id } = $page.params;

    export let user;
    export let scrum;

    let isDeletePopupOpen = false;

    const formatDate = (date) => {
        return `${date.getFullYear()}-${date.getMonth().toString().length == 1 ? '0'+date.getMonth() : date.getMonth()}-${date.getDate().toString().length == 1 ? '0'+date.getDate() : date.getDate()}`;
    }

    const deleteScrum = async () => {
        await fetch(`${variables.apiURL}/api/v1/scrum/${id}`, {
            method: 'DELETE',
            headers: {
                'token': user.token,
            }
        });
        window.location.replace(`/project/${scrum.projectOwner}/${scrum.projectName}`);
    };

    const markAsToDo = async () => {
        scrum.updated = formatDate(new Date());
        scrum.status = "to_do";

        await fetch(`${variables.apiURL}/api/v1/scrum/${id}/to_do`, {
            method: 'PATCH',
            headers: {
                'token': user.token,
            }
        });
    };

    const markAsInProgress = async () => {
        scrum.updated = formatDate(new Date());
        scrum.status = "in_progress";

        await fetch(`${variables.apiURL}/api/v1/scrum/${id}/in_progress`, {
            method: 'PATCH',
            headers: {
                'token': user.token,
            }
        });
    };

    const markAsDone = async () => {
        scrum.updated = formatDate(new Date());
        scrum.status = "done";

        await fetch(`${variables.apiURL}/api/v1/scrum/${id}/done`, {
            method: 'PATCH',
            headers: {
                'token': user.token,
            }
        });
    };
</script>

<svelte:head>
    <title>Scrum #{id} | scrumw</title>
</svelte:head>

<div style="position: relative;">
    <div class="popup {isDeletePopupOpen ? 'active' : ''}">
        <div class="popup-title">{scrum.title}</div>
        <div class="popup-desc">Are sure you want to delete this scrum?</div>
        <div class="popup-footer">
            <button class="btn btn-primary" on:click={() => isDeletePopupOpen = false}>Cancel</button>
            <button class="btn btn-danger" on:click={deleteScrum}>Delete</button>
        </div>
    </div>
    
    <div class="scrum {isDeletePopupOpen ? 'blur' : ''}">
        <div class="bordered">
            <div class="project-header">
                <FolderMinusIcon size="24" />
                <a href="/project/{scrum.projectOwner}/{scrum.projectName}" class="project-title">{scrum.projectOwner}<span class="text-muted">/</span>{scrum.projectName}</a>
            </div>
        
            <div class="df">
                <div class="subtitle">{scrum.title}</div>
                <div class="scrum-status-card">
                    {#if scrum.status.toLowerCase() == "in_progress"}
                        <span class="text-color-warning">In Progress</span>
                    {:else if scrum.status.toLowerCase() == "done"}
                        <span class="text-color-success">Done</span>
                    {:else}
                        <span class="text-color-danger">To Do</span>
                    {/if}
                </div>
            </div>
        
            <div class="scrum-desc">
                {scrum.description}
            </div>
        
            <div class="opened-by">· Created by<span class="text-muted" style="margin-left: 0;">:</span> <a href="/user/{scrum.createdBy}" class="user-link">{scrum.createdBy}</a></div>
            <div class="opened-by">· Updated<span class="text-muted" style="margin-left: 0;">:</span> {scrum.updated}</div>
        </div>
    
        {#if scrum.projectOwner == user.username || scrum.openedBy == user.username}
            <div class="df">
                {#if scrum.status.toLowerCase() == "to_do" || scrum.status.toLowerCase() == "to-do"}
                    <button class="btn btn-warning" on:click={markAsInProgress}>Mark as In-Progress</button>
                    <button class="btn btn-success" on:click={markAsDone}>Mark as Done</button>
                {:else if scrum.status.toLowerCase() == "in_progress" || scrum.status.toLowerCase() == "in-progress"}
                    <button class="btn btn-primary" on:click={markAsToDo}>Mark as To-Do</button>    
                    <button class="btn btn-success" on:click={markAsDone}>Mark as Done</button>
                {:else if scrum.status.toLowerCase() == "done"}
                    <button class="btn btn-primary" on:click={markAsToDo}>Mark as To-Do</button>    
                    <button class="btn btn-warning" on:click={markAsInProgress}>Mark as In-Progress</button>
                {/if}
            </div>

            <div class="divider"></div>

            <div class="df">
                <button class="btn btn-danger" on:click={() => isDeletePopupOpen = true}>Delete scrum</button>
            </div>
        {/if}
    </div>
</div>

<style>
    .user-link {
        font-size: 1rem;
        color: var(--link-color);
        transition: .2s linear;
    }
    .user-link:hover {
        text-decoration: underline;
        color: var(--color-primary-light);
    }
    .scrum {
        padding: 1.5rem;
        min-height: 80vh;
        transition: .2s ease-in-out;
    }
    .bordered {
        border: 1px solid var(--border-color);
        border-radius: .5rem;
        padding: 1rem;
        padding-bottom: 1.5rem;
        margin-bottom: 1.2rem;
    }
    .project-title {
        color: var(--link-color);
        font-size: 1.2rem;
        transition: .3s ease-in-out;
        font-weight: bold;
    }
    .project-header {
        display: flex;
        align-items: center;
        gap: .5rem;
        color: var(--text-color-secondary);
        width: fit-content;
    }
    .project-title:hover {
        color: var(--color-primary-light);
    }
    .text-muted {
        color: var(--text-color-secondary);
        margin: .25rem;
    }
    .subtitle {
        color: var(--text-color-primary);
        font-size: 1.5rem;
        margin-top: 1rem;
    }
    .df {
        display: flex;
        align-items: center;
        gap: 1rem;
    }
    .scrum-status-card {
        background-color: var(--background-secondary);
        color: var(--text-color-primary);
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .4rem;
        font-size: .9rem;
        margin-top: 1.2rem;
        line-height: 1;
    }
    .text-color-warning {
        color: var(--color-warning-light);
    }
    .text-color-danger {
        color: var(--color-danger-light);
    }
    .text-color-success {
        color: var(--color-success-light);
    }
    .scrum-desc {
        text-align: justify;
        margin-top: 1.2rem;
        margin-bottom: 1.2rem;
        color: var(--text-color-secondary);
        padding: 1rem;
        line-height: 1;
        background-color: var(--background-primary);
        border-radius: .5rem;
    }
    .opened-by {
        color: var(--text-color-secondary);
    }
    .blur {
        filter: blur(.4rem);
        user-select: none;
        pointer-events: none;
        transition: .2s ease-in-out;
    }
    .popup {
        opacity: 0;
        pointer-events: none;
        user-select: none;
        position: absolute;
        top: 0%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 5;
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        border-radius: .4rem;
        box-shadow: 0 0 1.25rem rgba(0,0,0,.7);
        transition: .2s ease-in-out;
    }
    .popup.active {
        top: 50%;
        opacity: 1;
        pointer-events: inherit;
        user-select: inherit;
    }
    .popup-title {
        padding: 1rem;
        font-size: 1.1rem;
        color: var(--text-color-primary);
        font-weight: bold;
        border-bottom: 1px solid var(--border-color);
    }
    .popup-desc {
        padding: 1rem;
        color: var(--text-color-secondary);
    }
    .popup-footer {
        padding: 1rem;
        font-size: .9rem;
        border-top: 1px solid var(--border-color);
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .divider {
        width: 100%;
        height: 1px;
        background-color: var(--border-color);
        margin-top: 1rem;
        margin-bottom: 1rem;
    }
</style>