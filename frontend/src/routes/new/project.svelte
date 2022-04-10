<main class="main">
    <div class="header">
        <div class="title">Create a new project</div>
    </div>

    <div class="section-divider"></div>

    <div class="project-name-section">
        <div class="owner">0l1v3rr</div>
        <div class="slash">/</div>
        <div class="name-input">
            <input class="input" type="text" placeholder="Project name">
        </div>
    </div>

    <div class="name-description">
        When it comes to naming your project, you should use kebab-case. 
        This means the project name should not contain any spaces.
    </div>

    <div class="section-divider"></div>

    <div class="description">
        <label for="project-description">Description</label>
        <input class="input" type="text" id="project-description" placeholder="This is an amazing project!">
    </div>

    <div class="section-divider"></div>

    <button class="btn btn-primary">Create project</button>
</main>

<style>
    .main {
        padding: 1rem;
        width: 60vw;
        margin-top: 1rem;
        margin-inline: auto;
    }
    .header {
        margin-bottom: 1rem;
    }
    .title {
        color: var(--text-color-primary);
        font-size: 1.5rem;
    }
    .section-divider {
        width: 100%;
        height: 1px;
        background-color: var(--border-color);
        margin-block: 1rem;
    }
    .project-name-section {
        display: flex;
        align-items: center;
        gap: .5rem;
    }
    .owner {
        padding: .5rem 1rem;
        border: 1px solid var(--border-color);
        border-radius: .5rem;
    }
    .slash {
        font-size: 1.5rem;
        color: var(--text-color-secondary);
        font-weight: bold;
    }
    .input {
        background-color: var(--background-primary);
        color: var(--text-color-primary);
        padding: .5rem 1rem;
        border-radius: .5rem;
        border: 1px solid var(--border-color);
        font-size: .9rem;
    }
    .name-description {
        font-size: .9rem;
        color: var(--text-color-secondary);
        margin-bottom: 1rem;
        margin-top: .5rem;
    }
    .description {
        display: flex;
        flex-direction: column;
        gap: .5rem;
    }
</style>