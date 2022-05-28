<script context="module">
    export async function load({ fetch, session, params }) {
        const userRes = await fetch(`http://localhost:8080/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`http://localhost:8080/api/v1/issues/${params.id}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });

        if(res.ok) {
            const issue = await res.json();
            return {
                props: { 
                    issue: issue, 
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
    import { FolderMinusIcon } from 'svelte-feather-icons';
    import { page } from '$app/stores';
    const { id } = $page.params;

    export let user;
    export let issue;

    let isDeletePopupOpen = false;

    const formatDate = (date) => {
        return `${date.getFullYear()}-${date.getMonth().toString().length == 1 ? '0'+date.getMonth() : date.getMonth()}-${date.getDate().toString().length == 1 ? '0'+date.getDate() : date.getDate()}`;
    }

    const closeIssue = async () => {
        issue.isOpen = false;
        issue.closed = formatDate(new Date());
        issue.closedBy = user.username;

        await fetch(`http://localhost:8080/api/v1/issues/${issue.id}`, {
            method: 'PATCH',
            headers: {
                'token': user.token,
            }
        });
    };

    const deleteIssue = async () => {
        await fetch(`http://localhost:8080/api/v1/issues/${issue.id}`, {
            method: 'DELETE',
            headers: {
                'token': user.token,
            }
        });
        window.location.replace(`/project/${issue.projectOwner}/${issue.projectName}`);
    };
</script>

<svelte:head>
    <title>Issue #{id} | scrumw</title>
</svelte:head>

<div style="position: relative;">
    <div class="popup {isDeletePopupOpen ? 'active' : ''}">
        <div class="popup-title">{issue.issueTitle}</div>
        <div class="popup-desc">Are sure you want to delete this issue?</div>
        <div class="popup-footer">
            <button class="btn btn-primary" on:click={() => isDeletePopupOpen = false}>Cancel</button>
            <button class="btn btn-danger" on:click={deleteIssue}>Delete</button>
        </div>
    </div>
    
    <div class="issue {isDeletePopupOpen ? 'blur' : ''}">
        <div class="bordered">
            <div class="project-header">
                <FolderMinusIcon size="24" />
                <a href="/project/{issue.projectOwner}/{issue.projectName}" class="project-title">{issue.projectOwner}<span class="text-muted">/</span>{issue.projectName}</a>
            </div>
        
            <div class="df">
                <div class="subtitle">Issue: {issue.issueTitle}</div>
                <div class="issue-status-card">
                    {#if issue.isOpen}
                        <span class="text-color-danger">Open</span>
                    {:else}
                        <span class="text-color-warning">Closed</span>
                    {/if}
                </div>
            </div>
        
            <div class="issue-desc">
                {issue.issueDescription}
            </div>
        
            <div class="opened-by">· Opened by<span class="text-muted" style="margin-left: 0;">:</span> <a href="/user/{issue.closedBy}" class="user-link">{issue.openedBy}</a> <span class="text-muted">({issue.opened})</span></div>
            {#if !issue.isOpen}
                <div class="opened-by">· Closed by<span class="text-muted" style="margin-left: 0;">:</span> <a href="/user/{issue.closedBy}" class="user-link">{issue.closedBy}</a> <span class="text-muted">({issue.closed})</span></div>
            {/if}
        </div>
    
        {#if issue.projectOwner == user.username || issue.openedBy == user.username}
            <div class="df">
                {#if issue.isOpen}
                    <button class="btn btn-primary" on:click={closeIssue}>Close issue</button>
                {/if}
                <button class="btn btn-danger" on:click={() => isDeletePopupOpen = true}>Delete issue</button>
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
    .issue {
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
    .issue-status-card {
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
    .issue-desc {
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
</style>