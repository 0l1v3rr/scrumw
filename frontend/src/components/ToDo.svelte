<div>
    <div class="todo-heading">
        <div class="todo-header">Daily Tasks</div>
        <a href="/todo" class="todo-subheader">View TODOs</a>
    </div>
    <div class="todo-form">
        <form>
            <div class="todo-form-section">
                <label for="title">To-Do Title</label>
                <input type="text" id="title" placeholder="Feed the cats" required>
            </div>
            <div class="todo-form-section">
                <label for="description">To-Do description</label>
                <textarea id="description" rows="8" placeholder="Buy them fish!" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Create!</button>
        </form>
    </div>
</div>

<style>
    .todo-heading {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .todo-header {
        color: var(--text-color-primary);
        font-weight: bold;
    }
    .todo-subheader {
        font-size: .85rem;
        color: var(--text-color-secondary);
    }
    .todo-subheader:hover {
        text-decoration: underline;
    }
    .todo-form {
        margin: 1rem 0;
        padding: 1rem;
        border-radius: .5rem;
        border: 2px dashed var(--border-color);
    }
    form label {
        color: var(--text-color-secondary);
        cursor: pointer;
        display: block;
        margin-bottom: .5rem;
        font-size: 1rem;
    }
    .todo-form-section {
        display: flex;
        justify-content: center;
        align-items: flex-start;
        flex-direction: column;
        margin-bottom: 1rem;
    }
    .btn {
        width: 100%;
    }
    #description,
    #title {
        color: var(--text-color-primary);
        padding: .5rem;
        width: 100%;
        border-radius: .4rem;
        border: 1px solid var(--border-color);
        background-color: var(--background-primary);
        font-size: .95rem;
    }
    #description:focus,
    #title:focus {
        border: 1px solid var(--color-primary);
    }
</style>