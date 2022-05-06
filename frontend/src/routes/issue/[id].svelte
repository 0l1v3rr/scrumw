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
</script>

<svelte:head>
    <title>Issue #{id} | scrumw</title>
</svelte:head>

<div class="issue">
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

    <div class="divider"></div>

    <div class="opened-by">· Opened by<span class="text-muted" style="margin-left: 0;">:</span> <b>{issue.openedBy}</b> <span class="text-muted">({issue.opened})</span></div>
    {#if !issue.isOpen}
        <div class="opened-by">· Closed by<span class="text-muted" style="margin-left: 0;">:</span> <b>{issue.closedBy}</b> <span class="text-muted">({issue.closed})</span></div>
    {/if}

    {#if issue.projectOwner == user.username || issue.openedBy == user.username}
        <div class="divider"></div>
        <div class="df">
            {#if issue.isOpen}
                <button class="btn btn-primary" on:click={closeIssue}>Close issue</button>
            {/if}
            <button class="btn btn-danger">Delete issue</button>
        </div>
    {/if}
</div>

<style>
    .issue {
        padding: 1.5rem;
        padding-top: .5rem;
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
        margin-top: .5rem;
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
        margin-top: .5rem;
    }
    .text-color-warning {
        color: var(--color-warning-light);
    }
    .text-color-danger {
        color: var(--color-danger-light);
    }
    .issue-desc {
        text-align: justify;
        margin-top: .5rem;
        margin-bottom: .5rem;
        color: var(--text-color-secondary);
    }
    .divider {
        background-color: var(--border-color);
        width: 100%;
        height: 1px;
        margin-top: 1rem;
        margin-bottom: 1rem;
    }
    .opened-by {
        color: var(--text-color-secondary);
    }
</style>