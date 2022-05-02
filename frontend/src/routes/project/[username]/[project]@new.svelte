<script context="module">
    export async function load({ fetch, session, params }) {
        const userRes = await fetch(`http://localhost:8080/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`http://localhost:8080/api/v1/projects/${params.username}/${params.project}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });

        if(res.ok) {
            const issuesRes = await fetch(`http://localhost:8080/api/v1/issues/${params.username}/${params.project}`, {
                method: 'GET',
                headers: {
                    'token': session.token,
                }
            });

            const project = await res.json();
            const issues = await issuesRes.json();
            return {
                props: { 
                    givenProject: project, 
                    issues: issues, 
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
    import { FolderMinusIcon, AlertCircleIcon, TrelloIcon } from 'svelte-feather-icons';
    import IssueCard from "../../../components/cards/IssueCard.svelte";
    import NotFound from "../../../components/cards/NotFound.svelte";

    import { page } from '$app/stores';
    const { username, project } = $page.params;

    export let givenProject;
    export let issues;
    export let user;
    export let status;

    let currentIssues;
    let openIssues = issues.filter(i => i.isOpen).length;
    let closedIssues = issues.filter(i => !i.isOpen).length;

    let isOpenSelected = true;
    let isClosedSelected = false;

    const getCurrentIssues = () => {
        if(isOpenSelected && isClosedSelected) {
            currentIssues = [...issues];
            return;
        }

        if(isOpenSelected) {
            currentIssues =  [...issues].filter(i => i.isOpen);
            return;
        }

        if(isClosedSelected) {
            currentIssues =  [...issues].filter(i => !i.isOpen);
            return;
        }

        currentIssues = [];
    };
    getCurrentIssues();

    const handleOpenClick = () => {
        isOpenSelected = !isOpenSelected;
        getCurrentIssues();
    };

    const handleClosedClick = () => {
        isClosedSelected = !isClosedSelected;
        getCurrentIssues();
    };

    const handleDeleteClick = async () => {
        // TODO verify the delete
        await fetch(`http://localhost:8080/api/v1/projects/${givenProject.id}`, {
            method: 'DELETE',
            headers: {
                'token': user.token,
            }
        });
        window.location = "/";
    };
</script>

<svelte:head>
    <title>{username}/{project} | scrumw</title>
</svelte:head>

<div class="project">
    {#if status == 200}
        <div class="project-header">
            <FolderMinusIcon size="24" />
            <a href="/project/{username}/{project}" class="project-title">{username}<span class="text-muted">/</span>{project}</a>
            { #if givenProject.isPublic }
                <div class="header-icon">Public</div>
            { :else }
                <div class="header-icon">Private</div>
            { /if }
        </div>

        <div class="d-flex">
            <div class="about">
                <div class="subtitle">
                    <div>About</div>
                    {#if username == user.username}
                        <button class="btn btn-primary">Edit</button>
                    {/if}
                </div>
                <p class="description">
                    {givenProject.projectDescription}
                </p>

                <div class="divider"></div>

                <div class="fdc">
                    <div class="about-d-flex">
                        <div class="about-icon"><AlertCircleIcon size="18" /></div>
                        <span>Open Issues: </span>
                        <b>{openIssues}</b>
                    </div>

                    <div class="about-d-flex">
                        <div class="about-icon"><AlertCircleIcon size="18" /></div>
                        <span>Closed Issues: </span>
                        <b>{closedIssues}</b>
                    </div>

                    <div class="about-d-flex">
                        <div class="about-icon"><TrelloIcon size="18" /></div>
                        <span>Scrums: </span>
                        <b>0</b>
                    </div>
                </div>

                <div class="divider"></div>
                
                <div class="d-flex-collab" style="align-items: flex-start;">
                    <div style="width: 50%;">
                        <div class="subtitle">
                            <div>Collaborators</div>
                        </div>
                        <div class="collaborator-list">
                            <a href="/user/{username}" class="collaborator">{username},</a>
                        </div>
                    </div>
                    <div class="add-collab">
                        <label for="add-collab">Add Collaborator</label>
                        <div class="d-flex-collab">
                            <input type="text" id="add-collab" placeholder="Username">
                            <button class="btn btn-success">Add</button>
                        </div>
                    </div>
                </div>

                {#if username == user.username}
                    <div class="divider"></div>

                    <div class="subtitle">
                        <div>Danger Zone</div>
                    </div>

                    <div class="danger-zone">
                        <button class="btn btn-warning">Change to {givenProject.isPublic ? 'Private' : 'Public'}</button>
                        <button class="btn btn-danger" on:click={handleDeleteClick}>Delete Project</button>
                    </div>
                {/if}
            </div>

            <div class="issues">
                <div class="subtitle">
                    <div>Issues</div>
                    <a href="/new/issue" class="btn btn-primary">New Issue</a>
                </div>
                <div class="issues-d-flex">
                    <div on:click={handleOpenClick} class="issues-section-header open {isOpenSelected ? 'active' : ''}">
                        <div class="header-title">Open</div>
                    </div>
                    <div on:click={handleClosedClick} class="issues-section-header closed {isClosedSelected ? 'active' : ''}">
                        <div class="header-title">Closed</div >
                    </div>
                </div>

                <div class="divider"></div>

                <div class="issues-cards">
                    {#if currentIssues.length == 0}
                        <NotFound searchQuery="issues" />
                    {:else}
                        {#each currentIssues as issue}
                            {#if issue.closedBy}
                                <IssueCard 
                                    projectOwner={issue.projectOwner}
                                    projectName={issue.projectName}
                                    issueTitle={issue.issueTitle}
                                    issueDescription={issue.issueDescription}
                                    isOpen={issue.isOpen}
                                    openedBy={issue.openedBy}
                                    closedBy={issue.closedBy}
                                />
                            {:else}
                                <IssueCard 
                                    projectOwner={issue.projectOwner}
                                    projectName={issue.projectName}
                                    issueTitle={issue.issueTitle}
                                    issueDescription={issue.issueDescription}
                                    isOpen={issue.isOpen}
                                    openedBy={issue.openedBy}
                                />
                            {/if}
                        {/each}
                    {/if}
                </div>
            </div>
        </div>
    {:else}
        <NotFound searchQuery="project {username}/{project}" />
    {/if}
</div>

<style>
    .project {
        padding: 1.5rem;
    }
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
    }
    .project-title:hover {
        color: var(--color-primary-light);
    }
    .header-icon {
        background-color: var(--background-secondary);
        color: var(--text-color-primary);
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .4rem;
        font-size: .9rem;
        margin-left: .75rem;
    }
    .text-muted {
        color: var(--text-color-secondary);
        margin: .25rem;
    }
    .description {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    .about {
        display: flex;
        justify-content: start;
        flex-direction: column;
        gap: .25rem;
        margin-top: .5rem;
        width: calc(50% - 1rem);
    }
    .subtitle {
        font-size: 1.1rem;
        font-weight: bold;
        color: var(--text-color-primary);
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .subtitle > button {
        font-size: .9rem;
    }
    .d-flex {
        display: flex;
        gap: 2rem;
    }
    .divider {
        width: 100%;
        height: 1px;
        margin-top: .5rem;
        margin-bottom: .5rem;
        background-color: var(--border-color);
    }
    .about-d-flex {
        display: flex;
        align-items: center;
        gap: .5rem;
        color: var(--text-color-secondary);
        font-size: .95rem;
        line-height: 1;
    }
    .fdc {
        display: flex;
        flex-direction: column;
        gap: .25rem;
    }
    .about-icon {
        padding: .5rem;
        background-color: var(--background-primary);
        line-height: 0;
        border-radius: .4rem;
    }
    .collaborator-list {
        display: flex;
        gap: .4rem;
    }
    .collaborator {
        color: var(--text-color-secondary);
        font-size: 1rem;
        transition: .3s ease-in-out;
        cursor: pointer;
    }
    .collaborator:hover {
        color: var(--link-color);
        text-decoration: underline;
    }
    .d-flex-collab {
        display: flex;
        gap: .5rem;
    }
    .add-collab > label {
        color: var(--text-color-secondary);
        font-size: .95rem;
        margin-bottom: .25rem;
        display: block;
    }
    #add-collab {
        padding: .25rem .5rem;
        background-color: var(--background-primary);
        color: var(--text-color-secondary);
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: .95rem;
    }
    #add-collab:focus {
        border-color: var(--color-primary);
    }
    .d-flex-collab > button {
        font-size: .9rem;
    }
    .danger-zone {
        display: flex;
        flex-direction: column;
        font-size: .95rem;
        gap: .25rem;
        align-items: start;
        justify-content: start;
        margin-top: .5rem;
    }
    .issues-section-header {
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: .5rem;
        width: fit-content;
        padding: .25rem .75rem;
        border: 1px solid var(--border-color);
        cursor: pointer;
        transition: .1s ease-in-out;
    }
    .issues-section-header.active {
        border-color: var(--text-color-secondary);
        transition: .1s ease-in-out;
    }
    .open > .header-title {
        color: var(--color-danger-light);
    }
    .closed > .header-title {
        color: var(--color-warning-light);
    }
    .issues-d-flex {
        display: flex;
        gap: .5rem;
        margin-top: .5rem;
        font-size: .95rem;
        margin-bottom: 1rem;
    }

    .issues {
        width: calc(50% - 1rem);
    }
    .issues-cards {
        display: flex;
        flex-direction: column;
        gap: .5rem;
        align-items: flex-start;
        justify-content: start;
        margin-top: 1rem;
    }

    @media screen and (max-width: 768px) {
        .issues {
            display: none;
        }
        .about {
            width: 100%;
        } 
    }
</style>