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
            const project = await res.json();
            return {
                props: { givenProject: project, status: res.status }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import { FolderMinusIcon, AlertCircleIcon, TrelloIcon } from 'svelte-feather-icons';

    import { page } from '$app/stores';
    const { username, project } = $page.params;

    export let givenProject;
    export let status;
</script>

<svelte:head>
    <title>{username}/{project} | scrumw</title>
</svelte:head>

<div class="project">
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
                <button class="btn btn-primary">Edit</button>
            </div>
            <p class="description">
                {givenProject.projectDescription}
            </p>

            <div class="divider"></div>

            <div class="fdc">
                <div class="about-d-flex">
                    <div class="about-icon"><AlertCircleIcon size="18" /></div>
                    <span>Open Issues: </span>
                    <b>0</b>
                </div>

                <div class="about-d-flex">
                    <div class="about-icon"><AlertCircleIcon size="18" /></div>
                    <span>Closed Issues: </span>
                    <b>0</b>
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

            <div class="divider"></div>

            <div class="subtitle">
                <div>Danger Zone</div>
            </div>

            <div class="danger-zone">
                <button class="btn btn-warning">Change to {givenProject.isPublic ? 'Private' : 'Public'}</button>
                <button class="btn btn-danger">Delete Project</button>
            </div>
        </div>
        <div class="issues">
            asd
        </div>
    </div>
</div>

<style>
    .project {
        padding: 1rem;
        padding-top: 0;
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
        justify-content: center;
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
        transition: .3s ease-in-out;
    }
    #add-collab:focus {
        border-color: var(--text-color-secondary);
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
</style>