<script context="module">
    export async function load({ fetch, session, params }) {
        const userRes = await fetch(`http://localhost:8080/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`http://localhost:8080/api/v1/users/${params.username}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const searchedUser = await res.json();

        const pres = await fetch(`http://localhost:8080/api/v1/projects/${params.username}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const projects = await pres.json();

        const ires = await fetch(`http://localhost:8080/api/v1/issues/${params.username}/count`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const issueCount = await ires.json();

        if(res.ok) {
            return {
                props: { 
                    status: res.status,
                    user: user,
                    searchedUser: searchedUser,
                    projects: projects,
                    issueCount: issueCount
                }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import {  TrelloIcon, AlertCircleIcon, EyeIcon } from 'svelte-feather-icons';
    import ProjectCard from '../../components/cards/ProjectCard.svelte';
    import NotFound from '../../components/cards/NotFound.svelte';
    import { page } from '$app/stores';

    const { username } = $page.params;

    export let user;
    export let searchedUser;
    export let projects;
    export let issueCount;

    let currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));

    let selectValue;

    const handleFilterOptionsChange = () => {
        if(selectValue == "latest-created") {
            currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
            return;
        }

        if(selectValue == "earliest-created") {
            currentProjects = [...projects].sort((a,b) => (a.created > b.created) ? 1 : ((b.created > a.created) ? -1 : 0));
            return;
        }

        if(selectValue == "project") {
            currentProjects = [...projects].sort((a,b) => (a.projectName > b.projectName) ? 1 : ((b.projectName > a.projectName) ? -1 : 0));
            return;
        }

        if(selectValue == "public") {
            currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
            currentProjects = currentProjects.filter(p => p.isPublic);
            return;
        }

        if(selectValue == "private") {
            currentProjects = [...projects].sort((a,b) => (a.created < b.created) ? 1 : ((b.created < a.created) ? -1 : 0));
            currentProjects = currentProjects.filter(p => !p.isPublic);
            return;
        }
    };
</script>

<svelte:head>
    <title>{username} | scrumw</title>
</svelte:head>

<div class="user-page">
    <div class="left-section">
        <div>
            <h1 class="title">{searchedUser.username}</h1>
            <p class="subtitle">{searchedUser.email}<span>{searchedUser.email == user.email ? ' · (you)' : ''}</span></p>
        </div>

        <div class="divider"></div>
        
        <div class="inforations">
            <div class="information-div">
                <EyeIcon size="16" /> <span class="bold">0</span> profile views
            </div>
            <div class="information-div">
                <TrelloIcon size="16" /> <span class="bold">0</span> scrums
            </div>
            <div class="information-div">
                <AlertCircleIcon size="16" /> <span class="bold">{issueCount.count}</span> issues
            </div>
        </div>

        
        {#if user.username != searchedUser.username}
            <div class="divider"></div>
            <button class="btn btn-primary">Message</button>
        {/if}
    </div>

    <div class="right-section">
        <div class="rs-subtitle">
            <div>Projects</div>
            <div class="project-count">{currentProjects.length}</div>
            <form class="header-form">
                <div class="select">
                    <label for="filter-by">Filter By: </label>
                    <select bind:value={selectValue} on:change={handleFilterOptionsChange} id="filter-by">
                        <option value="latest-created" selected>Latest Created</option>
                        <option value="earliest-created">Earliest Created</option>
                        <option value="project">Project name</option>
                        {#if user.username == searchedUser.username}
                            <option value="public">Public</option>
                            <option value="private">Private</option>
                        {/if}
                    </select>
                </div>
            </form>
        </div>
        {#if currentProjects.length == 0}
            <NotFound searchQuery="project" />
        {:else}
            {#each currentProjects as project}
                <ProjectCard 
                    owner={project.username}
                    name={project.projectName}
                    description={project.projectDescription}
                    isPublic={project.isPublic}
                    created={project.created}
                />
            {/each}
        {/if}
    </div>
</div>

<style>
    .header-form {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 1.2rem;
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .5rem;
        background-color: var(--background-primary);
        font-size: 1rem;
        margin-left: auto;
    }
    .select {
        display: flex;
        gap: .5rem;
        align-items: center;
        font-size: 1rem;
    }
    .select > label {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    #filter-by {
        padding: .25rem .5rem;
        background-color: var(--background-primary);
        color: var(--text-color-secondary);
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
        font-size: 1rem;
    }
    #filter-by:focus {
        border-color: var(--text-color-secondary);
    }
    .user-page {
        padding: 1rem;
        padding-top: 0;
        display: flex;
        align-items: start;
        gap: 2rem;
    }
    .left-section {
        display: flex;
        flex-direction: column;
        width: calc(35% - 1rem);
        gap: 1rem;
    }
    .right-section {
        width: calc(65% - 1rem);
        display: flex;
        align-items: start;
        justify-content: center;
        gap: .75rem;
        flex-direction: column;
    }
    .title {
        font-size: 2rem;
        font-weight: bold;
        color: var(--text-color-primary);
    }
    .subtitle {
        color: var(--text-color-secondary)
    }

    .inforations {
        display: flex;
        align-items: start;
        flex-direction: column;
        gap: .5rem;
    }
    .information-div {
        line-height: 1;
        display: flex;
        align-items: center;
        gap: .4rem;
        font-size: 1rem;
        color: var(--text-color-secondary);
    }
    .bold {
        font-weight: bold;
    }
    .divider {
        width: 100%;
        height: 1px;
        background-color: var(--border-color);
    }
    .rs-subtitle {
        font-size: 1.5rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: .75rem;
        width: 100%;
    }
    .project-count {
        font-size: 1rem;
        padding: .25rem 1rem;
        border-radius: .4rem;
        line-height: 1;
        border: 1px solid var(--border-color);
        color: var(--text-color-secondary);
    }

    @media screen and (max-width: 576px) {
        .user-page {
            flex-direction: column;
        }
        .right-section {
            width: 100%;
        }
        .left-section {
            width: 100%;
        }
    }
</style>