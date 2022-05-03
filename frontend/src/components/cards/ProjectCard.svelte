<script>
    import { FolderMinusIcon } from 'svelte-feather-icons';

    export let owner;
    export let name;
    export let description;
    export let isPublic = true;
    export let created = "2022 02 02";
</script>

<div class="project-card">
    <div class="project-header">
        <FolderMinusIcon size="20" />
        <a href="/project/{owner}/{name}" class="project-title">{owner}<span class="text-muted">/</span>{name}</a>
        { #if isPublic }
            <div class="header-icon">Public</div>
        { :else }
            <div class="header-icon">Private</div>
        { /if }
    </div>
    <div class="project-description">
        {description}
    </div>
    <div class="divider"></div>
    <div class="created">
        Created <b>{created}</b>
    </div>
</div>

<style>
    .divider {
        width: 100%;
        height: 1px;
        background-color: var(--border-color);
        margin-top: .75rem;
        margin-bottom: .5rem;
    }
    .created {
        color: var(--text-color-secondary);
        font-size: .9rem;
    }
    .project-card {
        width: 100%;
        background-color: var(--background-primary);
        padding: 1rem;
        border-radius: .5rem;
        border: 1px solid var(--border-color);
    }
    .project-title {
        color: var(--link-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
        font-weight: bold;
    }
    .project-header {
        display: flex;
        align-items: center;
        gap: .5rem;
        color: var(--text-color-secondary);
    }
    .project-title:hover {
        color: var(--color-primary-light);
    }
    .project-description {
        text-align: justify;
        font-size: .95rem;
        color: var(--text-color-secondary);
        margin: .5rem 0;
    }
    .text-muted {
        color: var(--text-color-secondary);
    }
    .header-icon {
        background-color: var(--background-secondary);
        color: var(--text-color-primary);
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .4rem;
        font-size: .9rem;
        margin-left: auto;
    }
</style>