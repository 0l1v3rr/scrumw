<script>
    export let projects;
    export let user;

    let optionValue;
    let scrumTitle;
    let scrumDescription;

    const handleFormSubmit = async () => {
        let splitted = optionValue.split("/");

        let newScrum = {
            projectOwner: splitted[0],
            projectName: splitted[1],
            title: scrumTitle,
            description: scrumDescription,
            createdBy: user.username
        }

        await fetch(`http://localhost:8080/api/v1/scrum`, {
            method: 'POST',
            headers: {
                'token': user.token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newScrum)
        });

        // TODO: redirect to the created scrum
    };
</script>

<div>
    <div class="scrum-heading">
        <div class="scrum-header">Add Scrum</div>
        <a href="/scrum-board" class="scrum-subheader">View all</a>
    </div>
    <div class="scrum-form">
        <form on:submit|preventDefault={handleFormSubmit}>
            <div class="scrum-form-section">
                <label for="project-select">Project</label>
                <select id="project-select" bind:value={optionValue}>
                    {#each projects as project}
                        <option value="{project.username}/{project.projectName}" selected>{project.username}/{project.projectName}</option>
                    {/each}
                </select>
            </div>
            <div class="scrum-form-section">
                <label for="title">Scrum Title</label>
                <input type="text" id="title" placeholder="Implement JDBC" bind:value={scrumTitle} required>
            </div>
            <div class="scrum-form-section">
                <label for="description">Description</label>
                <textarea id="description" rows="5" placeholder="Implementing JDBC template to communicate with our MySql database." bind:innerHTML={scrumDescription} contenteditable required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Create!</button>
        </form>
    </div>
</div>

<style>
    .scrum-heading {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .scrum-header {
        color: var(--text-color-primary);
        font-weight: bold;
    }
    .scrum-subheader {
        font-size: .85rem;
        color: var(--text-color-secondary);
    }
    .scrum-subheader:hover {
        text-decoration: underline;
    }
    .scrum-form {
        margin: 1rem 0;
        padding: 1rem;
        border-radius: .5rem;
        border: 1px solid var(--border-color);
    }
    form label {
        color: var(--text-color-secondary);
        cursor: pointer;
        display: block;
        margin-bottom: .5rem;
        font-size: 1rem;
    }
    .scrum-form-section {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        flex-direction: column;
        margin-bottom: 1rem;
    }
    .btn {
        width: 100%;
    }
    #project-select,
    #description,
    #title {
        color: var(--text-color-primary);
        padding: .5rem;
        width: 100%;
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        background-color: var(--background-primary);
        font-size: .95rem;
        font-family: 'Lato', Tahoma, Geneva, Verdana, sans-serif;
    }
    #project-select:focus,
    #description:focus,
    #title:focus {
        border: 1px solid var(--color-primary);
    }
    #project-select {
        color: var(--text-color-secondary);
    }
</style>