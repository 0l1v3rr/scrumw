<script context="module">
    export async function load({ fetch, session }) {
        const userRes = await fetch(`http://localhost:8080/api/v1/users/token/${session.token}`);
        const user = await userRes.json();

        const res = await fetch(`http://localhost:8080/api/v1/projects/${user.username}/latest`, {
            method: 'GET',
            headers: {
                'token': session.token,
            }
        });
        const projects = await res.json();

        if(res.ok) {
            return {
                props: { projects }
            };
        }

        return {
            status: res.status
        };
    }
</script>

<script>
    import ProjectCard from "../components/cards/ProjectCard.svelte";
    import IssueCard from "../components/cards/IssueCard.svelte";

    export let projects;
</script>

<svelte:head>
    <title>Dashboard | scrumw</title>
</svelte:head>

<main class="main">
    <div class="dashboard-header">
        <div class="dashboard-title">Dashboard</div>
    </div>

    <div class="scrums-container">
        
        <div class="scrum-section project-section">
            <div class="scrum-section-header">
                <div class="header-title">Recent Projects</div>
            </div>

            {#each projects as project}
                <ProjectCard 
                    owner={project.username}
                    name={project.projectName}
                    description={project.projectDescription}
                    isPublic={project.isPublic}
                />
            {/each}

        </div>

        <div class="scrum-section">
            <div class="scrum-section-header">
                <div class="header-title">Recent Issues</div>
            </div>

            <IssueCard 
                projectOwner="0l1v3rr"
                projectName="test-project"
                issueTitle="Test issue"
                issueDescription="Lorem ipsum, dolor sit amet consectetur adipisicing elit. Minus, et?"
                openedBy="j0hn-d03"
            />

            <IssueCard 
                projectOwner="0l1v3rr"
                projectName="test-project"
                issueTitle="Test issue"
                issueDescription="Lorem ipsum, dolor sit amet consectetur adipisicing elit. Minus, et?"
                isOpen={false}
                openedBy="j0hn-d03"
                closedBy="tr3v0r"
            />
        </div>

    </div>
</main>

<style>
    .dashboard-header {
        padding: 0 1rem;
    }
    .dashboard-title {
        color: var(--text-color-primary);
        font-weight: bold;
        font-size: 1.1rem;
    }
    .scrums-container {
        background-color: var(--background-secondary);
        display: flex;
        align-items: start;
        justify-content: center;
        padding: 1rem;
        gap: 1rem;
        height: 100%;
    }
    .scrum-section {
        width: 50%;
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }
    .scrum-section-header {
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: .5rem;
        width: fit-content;
        padding: .5rem 1rem;
        border: 1px solid var(--border-color);
    }
    .header-title {
        color: var(--text-color-secondary);
    }
    @media screen and (max-width: 576px) {
        .project-section {
            display: none;
        }
        .scrum-section {
            width: 100%;
        }
    }
</style>