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

        const issueRes = await fetch(`http://localhost:8080/api/v1/issues/user/${user.username}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const issues = await issueRes.json();

        return {
            props: { 
                projects,
                issues
            }
        };
    }
</script>

<script>
    import IssueCard from "../components/cards/IssueCard.svelte";
    import NotFound from "../components/cards/NotFound.svelte";

    export let projects;
    export let issues;
    let currentIssues = [...issues];

    let openIssues = currentIssues.filter(i => i.isOpen).length;
    let closedIssues = currentIssues.filter(i => !i.isOpen).length;

    let selectValue;

    const handleFilterOptionsChange = () => {
        if(selectValue == "all") {
            currentIssues = [...issues];
            openIssues = currentIssues.filter(i => i.isOpen).length;
            closedIssues = currentIssues.filter(i => !i.isOpen).length;
            return;
        }

        const splitted = selectValue.split('/');
        currentIssues = [...issues].filter(i => i.projectOwner == splitted[0] && i.projectName == splitted[1]);
        openIssues = currentIssues.filter(i => i.isOpen).length;
        closedIssues = currentIssues.filter(i => !i.isOpen).length;
    };

    function doesProjectHasIssue(username, projectName) {
        for(let i of issues) {
            if(i.projectOwner == username && i.projectName == projectName) {
                return true;
            }
        }
        return false;
    }
</script>

<svelte:head>
    <title>Issues | scrumw</title>
</svelte:head>

<main class="main">
    <div class="issues-header">
        <div class="issues-title">Issues</div>
        <form class="header-form">
            <div class="select">
                <label for="select-project">Project: </label>
                <select bind:value={selectValue} on:change={handleFilterOptionsChange} id="select-project">
                    <option value="all" selected>All</option>
                    {#each projects.filter(p => doesProjectHasIssue(p.username, p.projectName)) as project}
                        <option value={project.username+"/"+project.projectName}>{project.username}/{project.projectName}</option>
                    {/each}
                </select>
            </div>
        </form>
    </div>

    <div class="issues-container">
        <div class="issues-section open">
            <div class="issues-section-header open">
                <div class="header-title">Open</div>
                <div class="header-count">{openIssues}</div>
            </div>

            {#if openIssues == 0}
                <NotFound searchQuery="open issues" />
            {:else}
                {#each currentIssues.filter(i => i.isOpen) as issue}
                    {#if issue.closedBy}
                        <IssueCard 
                            id={issue.id}
                            projectOwner={issue.projectOwner}
                            projectName={issue.projectName}
                            issueTitle={issue.issueTitle}
                            issueDescription={issue.issueDescription}
                            isOpen={issue.isOpen}
                            openedBy={issue.openedBy}
                            closedBy={issue.closedBy}
                            opened={issue.opened}
                            closed={issue.closed}
                        />
                    {:else}
                        <IssueCard 
                            id={issue.id}
                            projectOwner={issue.projectOwner}
                            projectName={issue.projectName}
                            issueTitle={issue.issueTitle}
                            issueDescription={issue.issueDescription}
                            isOpen={issue.isOpen}
                            openedBy={issue.openedBy}
                            opened={issue.opened}
                        />
                    {/if}
                {/each}
            {/if}
        </div>

        <div class="issues-section closed">
            <div class="issues-section-header closed">
                <div class="header-title">Closed</div>
                <div class="header-count">{closedIssues}</div>
            </div>

            {#if closedIssues == 0}
                <NotFound searchQuery="closed issues" />
            {:else}
                {#each currentIssues.filter(i => !i.isOpen) as issue}
                    {#if issue.closedBy}
                        <IssueCard 
                            id={issue.id}
                            projectOwner={issue.projectOwner}
                            projectName={issue.projectName}
                            issueTitle={issue.issueTitle}
                            issueDescription={issue.issueDescription}
                            isOpen={issue.isOpen}
                            openedBy={issue.openedBy}
                            closedBy={issue.closedBy}
                            opened={issue.opened}
                            closed={issue.closed}
                        />
                    {:else}
                        <IssueCard 
                            id={issue.id}
                            projectOwner={issue.projectOwner}
                            projectName={issue.projectName}
                            issueTitle={issue.issueTitle}
                            issueDescription={issue.issueDescription}
                            isOpen={issue.isOpen}
                            openedBy={issue.openedBy}
                            opened={issue.opened}
                        />
                    {/if}
                {/each}
            {/if}
        </div>
    </div>
</main>

<style>
    .issues-header {
        padding: 0 1rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .issues-title {
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

    .issues-container {
        background-color: var(--background-secondary);
        display: flex;
        align-items: start;
        justify-content: center;
        padding: 1rem;
        gap: 1rem;
        height: 100%;
    }
    .issues-section {
        width: 50%;
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }
    .issues-section-header {
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
    .open > .header-title {
        color: var(--color-danger-light);
    }
    .open > .header-count {
        background-color: var(--color-danger);
    }
    .closed > .header-title {
        color: var(--color-warning-light);
    }
    .closed > .header-count {
        background-color: var(--color-warning);
        color: var(--background-primary);
    }
    @media screen and (max-width: 576px) {
        .issues-section.closed {
            display: none;
        }
        .issues-section {
            width: 100%;
        }
    }
</style>