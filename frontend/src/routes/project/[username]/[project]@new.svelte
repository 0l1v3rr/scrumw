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
    let isDeletePopupOpen = false;
    let isNewIssueOpen = false;
    let isUpdateModeOn = false;

    let newIssueTitle;
    let newIssueDesc;

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

    const formatDate = (date) => {
        return `${date.getFullYear()}-${date.getMonth().toString().length == 1 ? '0'+date.getMonth() : date.getMonth()}-${date.getDate().toString().length == 1 ? '0'+date.getDate() : date.getDate()}`;
    }

    const handleOpenClick = () => {
        isOpenSelected = !isOpenSelected;
        getCurrentIssues();
    };

    const handleClosedClick = () => {
        isClosedSelected = !isClosedSelected;
        getCurrentIssues();
    };

    const deleteProject = async () => {
        await fetch(`http://localhost:8080/api/v1/projects/${givenProject.id}`, {
            method: 'DELETE',
            headers: {
                'token': user.token,
            }
        });
        window.location = "/";
    };

    const handleChangeVisibility = async () => {
        givenProject.isPublic = !givenProject.isPublic;
        await fetch(`http://localhost:8080/api/v1/projects/${givenProject.id}`, {
            method: 'PATCH',
            headers: {
                'token': user.token,
            }
        });
    };

    const handleSave = async () => {
        isUpdateModeOn = false;
        await fetch(`http://localhost:8080/api/v1/projects/${givenProject.id}`, {
            method: 'PUT',
            headers: {
                'token': user.token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(givenProject)
        });
    };

    const createIssue = async () => {
        isNewIssueOpen = false;
        const date = new Date();

        const newIssue = {
            projectOwner: username,
            projectName: project,
            issueTitle: newIssueTitle,
            issueDescription: newIssueDesc,
            isOpen: true,
            openedBy: user.username,
            opened: formatDate(date),
            closedBy: null,
            closed: null
        };

        await fetch(`http://localhost:8080/api/v1/issues`, {
            method: 'POST',
            headers: {
                'token': user.token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newIssue)
        });

        issues.push(newIssue);
        openIssues++;

        getCurrentIssues();

        newIssueTitle = "";
        newIssueDesc = "";
    };
</script>

<svelte:head>
    <title>{username}/{project} | scrumw</title>
</svelte:head>

<div style="position: relative;">
    {#if isDeletePopupOpen}
        <div class="popup">
            <div class="popup-title">{username}/{project}</div>
            <div class="popup-desc">Are sure you want to delete this project?</div>
            <div class="popup-footer">
                <button class="btn btn-primary" on:click={() => isDeletePopupOpen = false}>Cancel</button>
                <button class="btn btn-danger" on:click={deleteProject}>Delete</button>
            </div>
        </div>
    {/if}

    {#if isNewIssueOpen}
        <div class="popup">
            <div class="popup-title">Open a new issue - {username}/{project}</div>
            <div class="popup-desc">
                <div class="add-collab">
                    <label for="issue-title">Issue Title</label>
                    <input type="text" id="issue-title" placeholder="This is an issue" bind:value={newIssueTitle}>
                </div>
                <div class="divider" style="margin-top: 1rem; margin-bottom: .5rem;"></div>
                <div class="add-collab">
                    <label for="issue-desc">Issue Description</label>
                    <input type="text" id="issue-desc" placeholder="This is an issue" bind:value={newIssueDesc}>
                </div>
            </div>
            <div class="popup-footer">
                <button class="btn btn-primary" on:click={() => isNewIssueOpen = false}>Cancel</button>
                <button class="btn btn-success" on:click={createIssue}>Create Issue</button>
            </div>
        </div>
    {/if}

    <div class="project {isDeletePopupOpen || isNewIssueOpen ? 'blur' : ''}">
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
                            {#if isUpdateModeOn}
                                <button class="btn btn-success" on:click={handleSave}>Save</button>
                            {:else}
                                <button class="btn btn-primary" on:click={() => isUpdateModeOn = true}>Edit</button>
                            {/if}
                        {/if}
                    </div>
                    <p class="description">
                        {#if isUpdateModeOn}
                            <input class="edit-input" type="text" placeholder="Description" bind:value={givenProject.projectDescription}>
                        {:else}
                            {givenProject.projectDescription}
                        {/if}
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
                        {#if username == user.username}
                            <div class="add-collab">
                                <label for="add-collab">Add Collaborator</label>
                                <div class="d-flex-collab">
                                    <input type="text" id="add-collab" placeholder="Username">
                                    <button class="btn btn-success">Add</button>
                                </div>
                            </div>
                        {/if}
                    </div>

                    {#if username == user.username}
                        <div class="divider"></div>

                        <div class="subtitle">
                            <div>Danger Zone</div>
                        </div>

                        <div class="danger-zone">
                            <button class="btn btn-warning" on:click={handleChangeVisibility}>Change to {givenProject.isPublic ? 'Private' : 'Public'}</button>
                            <button class="btn btn-danger" on:click={() => isDeletePopupOpen = true}>Delete Project</button>
                        </div>
                    {/if}
                </div>

                <div class="issues">
                    <div class="subtitle">
                        <div>Issues</div>
                        <button class="btn btn-primary" on:click={() => isNewIssueOpen = true}>New Issue</button>
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
            </div>
        {:else}
            <NotFound searchQuery="project {username}/{project}" />
        {/if}
    </div>
</div>

<style>
    .blur {
        filter: blur(.35rem);
        user-select: none;
        pointer-events: none;
        transition: .2s ease-in-out;
    }
    .popup {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 5;
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        border-radius: .4rem;
        box-shadow: 0 0 1.25rem rgba(0,0,0,.7);
        transition: .2s ease-in-out;
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

    .project {
        padding: 1.5rem;
        transition: .2s ease-in-out;
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
    #issue-desc,
    #issue-title,
    .edit-input,
    #add-collab {
        padding: .4rem .5rem;
        background-color: var(--background-primary);
        color: var(--text-color-secondary);
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: .95rem;
    }
    #issue-desc,
    #issue-title,
    .edit-input {
        width: 100%;
    }
    #issue-desc:focus,
    #issue-title:focus,
    .edit-input:focus,
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
        padding: .4rem .75rem;
        border: 1px solid var(--border-color);
        cursor: pointer;
        transition: .1s ease-in-out;
        line-height: 1;
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