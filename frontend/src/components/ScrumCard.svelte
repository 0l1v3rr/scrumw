<div>
    <div class="scrum-heading">
        <div class="scrum-header">Add Scrum</div>
        <a href="/scrum-board" class="scrum-subheader">View all</a>
    </div>
    <div class="scrum-form">
        <form>
            <div class="scrum-form-section">
                <label for="project-select">Project</label>
                <select id="project-select">
                    <option value="all" selected>All</option>
                    <option value="project-1">0l1v3rr/project-1</option>
                </select>
            </div>
            <div class="scrum-form-section">
                <label for="title">Scrum Title</label>
                <input type="text" id="title" placeholder="Implement JDBC" required>
            </div>
            <div class="scrum-form-section">
                <label for="description">Description</label>
                <textarea id="description" rows="8" placeholder="Implementing JDBC template to communicate with our MySql database." required></textarea>
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