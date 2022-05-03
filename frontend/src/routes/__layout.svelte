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
        if(res.ok) {
            return {
                props: { 
                    projects: projects,
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
    import Header from "../components/Header.svelte";
    import Sidebar from "../components/Sidebar.svelte";
    import RightSidebar from "../components/RightSidebar.svelte";
    import DashboardPanel from "../components/DashboardPanel.svelte";

    export let projects;
    export let user;
</script>

<Sidebar />
<Header user={user} />
<div class="wrapper">
    <div class="slot">
        <DashboardPanel user={user} />
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
        }
    }
</style>