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

        if(res.ok) {
            return {
                props: { 
                    status: res.status,
                    user: user,
                    searchedUser: searchedUser,
                    projects: projects
                }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import {  FolderMinusIcon, TrelloIcon, AlertCircleIcon } from 'svelte-feather-icons';
    import ProjectCard from '../../components/cards/ProjectCard.svelte';
    import { page } from '$app/stores';

    const { username } = $page.params;

    export let user;
    export let searchedUser;
    export let projects;
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
                <FolderMinusIcon size="16" /> <span class="bold">0</span> projects
            </div>
            <div class="information-div">
                <TrelloIcon size="16" /> <span class="bold">0</span> scrums
            </div>
            <div class="information-div">
                <AlertCircleIcon size="16" /> <span class="bold">0</span> issues
            </div>
        </div>

        <div class="divider"></div>

        {#if user.username == searchedUser.username}
            <button class="btn btn-primary">Edit Profile</button>
        {:else}
            <button class="btn btn-primary">Send Friend Request</button>
        {/if}
    </div>

    <div class="right-section">
        <div class="rs-subtitle">Projects</div>
        {#each projects as project}
            <ProjectCard 
                owner={project.username}
                name={project.projectName}
                description={project.projectDescription}
                isPublic={project.isPublic}
            />
        {/each}
    </div>
</div>

<style>
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
    }
</style>