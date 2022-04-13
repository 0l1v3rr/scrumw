<script>
    import { FolderMinusIcon } from 'svelte-feather-icons';

    export let projectOwner;
    export let projectName;
    export let issueTitle;
    export let issueDescription;
    export let openedBy;
    export let closedBy = "";
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
    <div class="df">
        <a href="/{projectOwner}/{projectName}/issues" class="issue-main-title">{issueTitle}</a>
        <div class="issue-status-card">
            { #if isOpen }
                Open
            { :else }
                Closed
            { /if }
        </div>
    </div>
    <div class="issue-description">
        {issueDescription}
    </div>
    <div class="issue-footer">
        <div class="footer-text">
            <div>Opened by <a href="/{openedBy}" class="footer-text-bold">{openedBy}</a>.</div>
            { #if closedBy != "" }
                <div style="margin-top: .4rem;">Closed by <a href="/{closedBy}" class="footer-text-bold">{closedBy}</a>.</div>
            { /if }
        </div>
    </div>
</div>

<style>
    .df {
        display: flex;
        align-items: center;
        gap: .5rem;
    }
    .issue-status-card {
        background-color: var(--background-secondary);
        color: var(--text-color-primary);
        border: 1px solid var(--border-color);
        padding: .25rem .5rem;
        border-radius: .4rem;
        font-size: .9rem;
    }
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
        margin-bottom: .25rem;
    }
    .issue-main-title:hover {
        text-decoration: underline;
    }

    .issue-footer {
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
    .footer-text-bold:hover {
        text-decoration: underline;
    }
</style>