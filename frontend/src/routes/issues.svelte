<script>
    import IssueCard from "../components/cards/IssueCard.svelte";
</script>

<svelte:head>
    <title>Issues | scrumw</title>
</svelte:head>

<main class="main">
    <div class="issues-header">
        <div class="issues-title">Issues</div>
        <form class="header-form">
            <div class="select">
                <label for="select-project">Project: </label>
                <select id="select-project">
                    <option value="all" selected>All</option>
                    <option value="project-1">0l1v3rr/project-1</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
    </div>

    <div class="issues-container">
        <div class="issues-section open">
            <div class="issues-section-header open">
                <div class="header-title">Open</div>
                <div class="header-count">13</div>
            </div>

            <IssueCard 
                projectOwner="0l1v3rr"
                projectName="test-project"
                issueTitle="Test issue"
                issueDescription="Lorem ipsum, dolor sit amet consectetur adipisicing elit. Minus, et?"
                openedBy="j0hn-d03"
            />
        </div>

        <div class="issues-section closed">
            <div class="issues-section-header closed">
                <div class="header-title">Closed</div>
                <div class="header-count">2</div>
            </div>

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
    .issues-header {
        padding: 0 1rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .issues-title {
        color: var(--text-color-primary);
        font-weight: bold;
        font-size: 1.1rem;
    }
    .header-form {
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 1.2rem;
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .5rem;
        background-color: var(--background-primary);
    }
    .select {
        display: flex;
        gap: .5rem;
        align-items: center;
    }
    .select > label {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    #select-project {
        padding: .25rem .5rem;
        background-color: var(--background-primary);
        color: var(--text-color-secondary);
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
    }
    #select-project:focus {
        border-color: var(--text-color-secondary);
    }

    .issues-container {
        background-color: var(--background-secondary);
        display: flex;
        align-items: start;
        justify-content: center;
        padding: 1rem;
        gap: 1rem;
        height: 100%;
    }
    .issues-section {
        width: 50%;
    }
    .issues-section-header {
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: .5rem;
        width: fit-content;
        padding: .5rem 1rem;
        border: 1px solid var(--border-color);
        margin-bottom: 1rem;
    }
    .header-count {
        margin-left: .6rem;
        font-size: .9rem;
        border-radius: 50%;
        padding: .25rem .5rem;
    }
    .open > .header-title {
        color: var(--color-danger-light);
    }
    .open > .header-count {
        background-color: var(--color-danger);
    }
    .closed > .header-title {
        color: var(--color-success-light);
    }
    .closed > .header-count {
        background-color: var(--color-success);
    }
    @media screen and (max-width: 576px) {
        .issues-section.closed {
            display: none;
        }
        .issues-section {
            width: 100%;
        }
    }
</style>