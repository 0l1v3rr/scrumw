<svelte:head>
    <title>Dashboard | scrumw</title>
</svelte:head>

<script>
    import ScrumCard from "../components/scrum/ScrumCard.svelte";
</script>

<main class="main">
    <div class="dashboard-header">
        <div class="dashboard-title">Lates Activity</div>
    </div>

    <div class="scrums-container">
        
        <div class="scrum-section">
            <div class="scrum-section-header tasks">
                <div class="header-title">Tasks</div>
                <div class="header-count">2</div>
            </div>

            <ScrumCard 
                project="0l1v3rr/test"
                description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam, cumque!"
                title="Test scrum"
                assignedBy="John Doe"
            />

        </div>

        <div class="scrum-section in-progress-section">
            <div class="scrum-section-header in-progress">
                <div class="header-title">In Progress</div>
                <div class="header-count">2</div>
            </div>

            <ScrumCard 
                project="0l1v3rr/test"
                description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam, cumque!"
                title="Test scrum"
                assignedBy="John Doe"
            />
        </div>

        <div class="scrum-section">
            <div class="scrum-section-header done">
                <div class="header-title">Done</div>
                <div class="header-count">2</div>
            </div>

            <ScrumCard 
                project="0l1v3rr/test"
                description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Numquam, cumque!"
                title="Test scrum"
                assignedBy="John Doe"
                approvedBy="Tom Gregory"
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
        margin-top: 1rem;
    }
    .scrum-section {
        width: 33%;
    }
    .scrum-section-header {
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: rgba(255,255,255,.025);
        border-radius: .5rem;
        width: fit-content;
        padding: .5rem 1rem;
    }
    .header-count {
        margin-left: .6rem;
        font-size: .9rem;
        border-radius: 50%;
        padding: .25rem .5rem;
    }
    .tasks > .header-title {
        color: var(--color-danger-light);
    }
    .tasks > .header-count {
        background-color: var(--color-danger);
    }
    .in-progress > .header-title {
        color: var(--color-warning-light);
    }
    .in-progress > .header-count {
        background-color: var(--color-warning);
        color: var(--background-primary);
    }
    .done > .header-title {
        color: var(--color-success-light);
    }
    .done > .header-count {
        background-color: var(--color-success);
    }

    @media screen and (max-width: 576px) {
        .in-progress-section {
            display: none;
        }
        .scrum-section {
            width: 50%;
        }
    }
</style>