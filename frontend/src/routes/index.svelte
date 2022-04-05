<svelte:head>
    <title>Dashboard | scrumw</title>
</svelte:head>

<main class="main">
    <div class="dashboard-header">
        <div class="dashboard-title">Lates Scrums</div>
    </div>

    <div class="scrums-container">
        
        <div class="scrum-section">
            <div class="scrum-section-header tasks">
                <div class="header-title">Tasks</div>
                <div class="header-count">2</div>
            </div>

            <div class="scrum-card">
                <div class="scrum-card-project">0l1v3rr/test</div>
                <div class="scrum-card-title">Scrum To Do</div>
                <div class="scrum-card-description">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere cum ullam tenetur eius, quo molestiae.</div>
            </div>

        </div>

        <div class="scrum-section in-progress-section">
            <div class="scrum-section-header in-progress">
                <div class="header-title">In Progress</div>
                <div class="header-count">2</div>
            </div>

            <div class="scrum-card">
                <div class="scrum-card-project">0l1v3rr/test</div>
                <div class="scrum-card-title">Scrum In Progress</div>
                <div class="scrum-card-description">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Asperiores minima odit accusantium inventore commodi qui vero doloribus pariatur expedita voluptatem!</div>
                <div class="scrum-card-footer">
                    <div class="footer-text">
                        <div>Assigned by <span class="footer-text-bold">John Doe</span>.</div>
                    </div>
                </div>
            </div>
        </div>

        <div class="scrum-section">
            <div class="scrum-section-header done">
                <div class="header-title">Done</div>
                <div class="header-count">2</div>
            </div>

            <div class="scrum-card">
                <div class="scrum-card-project">0l1v3rr/test</div>
                <div class="scrum-card-title">Scrum In Progress</div>
                <div class="scrum-card-description">Lorem ipsum dolor sit amet consectetur, adipisicing elit. Facere cum ullam tenetur.</div>
                <div class="scrum-card-footer">
                    <div class="footer-text">
                        <div>Sent by <span class="footer-text-bold">John Doe</span>.</div>
                        <div>Approved by <span class="footer-text-bold">Tom Gregory</span>.</div>
                    </div>
                </div>
            </div>
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

    .scrum-card {
        margin-top: 1rem;
        padding: 1rem;
        background-color: var(--background-primary);
        border-radius: .5rem;
    }
    .scrum-card-project {
        color: var(--text-color-primary);
        margin-bottom: .5rem;
    }
    .scrum-card-title {
        color: var(--text-color-primary);
        font-size: 1rem;
        font-weight: bold;
        margin-bottom: .5rem;
    }
    .scrum-card-description {
        color: var(--text-color-secondary);
        font-size: .95rem;
        text-align: justify;
    }
    .scrum-card-footer {
        margin-top: 1rem;
        padding: .5rem 0;
        padding-bottom: 0;
        border-top: 1px solid var(--border-color);
        display: flex;
        align-items: center;
    }
    .footer-text {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    .footer-text-bold {
        font-weight: bold;
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