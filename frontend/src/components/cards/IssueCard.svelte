<script>
    import { FolderMinusIcon } from 'svelte-feather-icons';

    export let projectOwner;
    export let projectName;
    export let issueTitle;
    export let issueDescription;
    export let isPublic = true;
    export let isOpen = true;
</script>

<div class="issue-card">
    <div class="issue-header">
        <FolderMinusIcon size="20" />
        <a href="/{projectOwner}/{projectName}" class="issue-title">{projectOwner}<span class="text-muted">/</span>{projectName}</a>
        { #if isPublic }
            <div class="header-icon">Public</div>
        { :else }
            <div class="header-icon">Private</div>
        { /if }
    </div>
    <a href="/{projectOwner}/{projectName}/issues" class="issue-main-title">{issueTitle}</a>
    <div class="issue-description">
        {issueDescription}
    </div>
</div>

<style>
    .issue-card {
        width: 100%;
        background-color: var(--background-primary);
        padding: 1rem;
        border-radius: .5rem;
        border: 1px solid var(--border-color);
    }
    .issue-title {
        color: var(--link-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
        font-weight: bold;
    }
    .issue-header {
        display: flex;
        align-items: center;
        gap: .5rem;
        color: var(--text-color-secondary);
    }
    .issue-title:hover {
        color: var(--color-primary-light);
    }
    .issue-description {
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
    .issue-main-title {
        font-size: 1.1rem;
        margin-top: .25rem;
        display: flex;
        align-items: center;
        gap: .5rem;
        width: fit-content;
        color: var(--text-color-primary);
    }
    .issue-main-title:hover {
        text-decoration: underline;
    }
</style>