<script>
    import { FolderMinusIcon } from 'svelte-feather-icons';

    export let projectOwner;
    export let projectName;
    export let title;
    export let description;
    export let createdBy;
    export let updated;
</script>

<div class="scrum-card">
    <div class="issue-header">
        <FolderMinusIcon size="20" />
        <a href="/project/{projectOwner}/{projectName}" class="issue-title">{projectOwner}<span class="text-muted">/</span>{projectName}</a>
    </div>

    <div class="scrum-card-title">{ title }</div>
    <div class="scrum-card-description">{ description }</div>

    <div class="scrum-card-footer">
        <div class="footer-text">
            <div>Created by <a href="/user/{createdBy}" class="footer-text-bold">{createdBy}</a>.</div>
            <div style="margin-top: .5rem;">Updated <span class="footer-text-bold-2">{updated}</span></div>
        </div>
    </div>
    
</div>

<style>
    .scrum-card {
        margin-top: 1rem;
        padding: 1rem;
        background-color: var(--background-primary);
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
    .scrum-card-title {
        color: var(--text-color-primary);
        font-size: 1rem;
        font-weight: bold;
        margin-bottom: .5rem;
        margin-top: .75rem;
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
        color: var(--text-color-secondary);
    }
    .footer-text-bold-2 {
        font-weight: bold;
        color: var(--text-color-secondary);
        text-decoration: none;
    }
    .footer-text-bold:hover {
        text-decoration: underline;
    }
</style>