<script>
    let isError = false;
    let errorMsg;

    const handleError = (errMsg) => {
        errorMsg = errMsg;
        isError = true;
    };

    let projectName;
    let description;

    const handleButtonClick = () => {
        if(projectName == "" || !projectName) {
            handleError("Your project's name must not be empty.");
            return;
        }

        if(description == "" || !description) {
            handleError("Your description must not be empty.");
            return;
        }

        if(projectName.length > 32) {
            handleError("Your project's name cannot be longer than 32 characters.");
            return;
        }

        if(description.length > 256) {
            handleError("Your description cannot be longer than 256 characters.");
            return;
        }

        if(!projectName.match("^([a-z][a-z0-9]*)(-[a-z0-9]+)*$")) {
            handleError("Your project's name must be kebab-case!");
            return;
        }
    };
</script>

<svelte:head>
    <title>New Project | scrumw</title>
</svelte:head>

<main class="main new">
    <div class="header">
        <div class="title">Create a new project</div>
    </div>

    <div class="section-divider"></div>

    <div class="project-name-section">
        <div class="owner">0l1v3rr</div>
        <div class="slash">/</div>
        <div class="name-input">
            <input class="input" type="text" placeholder="Project name" bind:value={projectName}>
        </div>
    </div>

    <div class="name-description">
        When it comes to naming your project, you should use kebab-case. 
        This means the project name should not contain any spaces.
    </div>

    <div class="section-divider"></div>

    <div class="description">
        <label for="project-description">Description</label>
        <input class="input" type="text" id="project-description" placeholder="This is an amazing project!" bind:value={description}>
    </div>

    <div class="section-divider"></div>

    {#if isError}
        <div class="error">
            <div class="error-msg">{errorMsg}</div>
            <div class="section-divider"></div>
        </div>
    {/if}

    <button class="btn btn-primary" on:click={handleButtonClick}>Create project</button>
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
    .input:focus {
        border: 1px solid var(--color-primary);
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
    .error-msg {
        border: 1px solid var(--color-danger);
        color: var(--color-danger-light);
        border-radius: .5rem;
        padding: .5rem 1rem;
    }
    @media screen and (max-width: 576px) {
        .main {
            width: 100vw;
            padding: 2rem;
        }
    }
</style>