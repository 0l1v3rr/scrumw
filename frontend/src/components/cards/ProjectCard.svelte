<script>
    import { FolderMinusIcon, AlertCircleIcon, TrelloIcon } from 'svelte-feather-icons';

    export let owner;
    export let name;
    export let description;
    export let isPublic = true;
    export let scrumCount = 0;
    export let issueCount = 0;
</script>

<div class="project-card">
    <div class="project-header">
        <FolderMinusIcon size="20" />
        <a href="/{owner}/{name}" class="project-title">{owner}<span class="text-muted">/</span>{name}</a>
        { #if isPublic }
            <div class="header-icon">Public</div>
        { :else }
            <div class="header-icon">Private</div>
        { /if }
    </div>
    <div class="project-description">
        {description}
    </div>
    <div class="project-footer">
        <a href="/{owner}/{name}/issues" class="project-footer-icon pfi-issues"><AlertCircleIcon size="20" /> {issueCount}</a>
        <a href="/{owner}/{name}/scrum" class="project-footer-icon pfi-scrums"><TrelloIcon size="20" /> {scrumCount}</a>
    </div>
</div>

<style>
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
    .project-footer {
        display: flex;
        align-items: center;
        gap: 1rem;
        margin-top: 1rem;
    }
    .project-footer-icon {
        background-color: var(--background-secondary);
        color: var(--text-color-primary);
        border: 1px solid var(--border-color);
        padding: .4rem .5rem;
        border-radius: .4rem;
        font-size: .95rem;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: .75rem;
        transition: .3s ease-in-out;
    }
    .pfi-issues {
        color: var(--color-warning-light);
    }
    .pfi-scrums {
        color: var(--color-success-light);
    }
    .project-footer-icon:hover {
        border: 1px solid var(--text-color-secondary);
    }
</style>