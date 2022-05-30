<script context="module">
    export async function load({ fetch, session }) {
        const userRes = await fetch(`${session.apiURL}/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`${session.apiURL}/api/v1/projects/${user.username}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const projects = await res.json();

        const issueRes = await fetch(`${session.apiURL}/api/v1/issues/user/${user.username}`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const issues = await issueRes.json();

        const scrumsRes = await fetch(`${session.apiURL}/api/v1/scrum/${user.username}`, {
            method: 'GET',
            headers: {
                "token": session.token
            }
        });
        const scrums = await scrumsRes.json();

        return {
            props: { 
                projects: projects,
                user: user,
                issues: issues,
                scrums: scrums,
            }
        };
    }
</script>

<script>
    import Header from "../components/Header.svelte";
    import Sidebar from "../components/Sidebar.svelte";
    import RightSidebar from "../components/RightSidebar.svelte";
    import DashboardPanel from "../components/DashboardPanel.svelte";

    export let projects;
    export let user;
    export let issues;
    export let scrums;
</script>

<Sidebar />
<Header user={user} />
<div class="wrapper">
    <div class="slot">
        <DashboardPanel user={user} projects={projects} issues={issues} scrums={scrums} />
        <slot></slot>
    </div>
    <RightSidebar projects={projects} user={user} />
</div>

<style>
    .wrapper {
        display: flex;
        align-items: start;
        padding-top: 4rem;
    }
    .slot {
        padding-left: var(--sidebar-width);
        padding-right: 22vw;
        width: 100%;
    }

    @media screen and (max-width: 922px) {
        .slot {
            padding-right: 0;
        }
    }
    @media screen and (max-width: 576px) {
        .slot {
            padding-left: 0;
            margin-bottom: 5rem;
        }
    }
</style>