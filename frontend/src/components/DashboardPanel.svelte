<script>
    import {  FolderMinusIcon, TrelloIcon, AlertCircleIcon } from 'svelte-feather-icons';

    // export let user;
    export let projects;
    export let issues;
    export let scrums;
</script>

<div class="dashboard-panel">
    <div class="dashboard-panel-item dpi-projects">
        <div class="dashboard-title">
            <div class="dashboard-icon" style="color: var(--color-warning);"><FolderMinusIcon size="24" /></div>
            <div class="dashboard-title-text">Projects</div>
        </div>
        <div class="dashboard-content">
            <div class="content-number">{projects ? projects.length : 0}</div>
            <div class="content-subtitle"><span class="content-subnumber">{projects.filter(p => !p.isPublic) ? projects.filter(p => !p.isPublic).length : 0}</span> private</div>
        </div>
    </div>

    <div class="dashboard-panel-item dpi-issues">
        <div class="dashboard-title" style="color: var(--color-danger);">
            <div class="dashboard-icon"><AlertCircleIcon size="24" /></div>
            <div class="dashboard-title-text">Total Issues</div>
        </div>
        <div class="dashboard-content">
            <div class="content-number">{issues ? issues.length : 0}</div>
            <div class="content-subtitle"><span class="content-subnumber">{issues.filter(x => x.isOpen) ? issues.filter(x => x.isOpen).length : 0}</span> open</div>
        </div>
    </div>

    <div class="dashboard-panel-item dpi-tasks">
        <div class="dashboard-title">
            <div class="dashboard-icon" style="color: var(--color-success-light);"><TrelloIcon size="24" /></div>
            <div class="dashboard-title-text">Total Scrums</div>
        </div>
        <div class="dashboard-content">
            <div class="content-number">{scrums ? scrums.length : 0}</div>
            <div class="content-subtitle"><span class="content-subnumber">{scrums.filter(s => s.status.toLowerCase() === "done") ? scrums.filter(s => s.status.toLowerCase() === "done").length : 0}</span> completed</div>
        </div>
    </div>

</div>
<div class="dashboard-divider"></div>

<style>
    .dashboard-panel {
        display: flex;
        margin: 1rem;
        align-items: center;
        justify-content: center;
        gap: 1rem;
    }
    .dashboard-panel-item {
        width: 33%;
        border: 1px solid var(--border-color);
        padding: 1rem;
        border-radius: .5rem;
        transition: .3s ease-in-out;
        background-color: var(--background-primary);
    }
    .dashboard-title {
        display: flex;
        align-items: center;
    }
    .dashboard-icon {
        padding: .5rem .75rem;
        background-color: var(--background-secondary);
        border-radius: .5rem;
        line-height: 1;
    }
    .dashboard-title-text {
        margin-left: 1rem;
        color: var(--text-color-primary);
        font-weight: 500;
    }
    .dashboard-content {
        display: flex;
        align-items: center;
        margin-top: 1rem;
    }
    .content-number {
        font-size: 2rem;
        color: var(--text-color-primary);
        font-weight: bold;
        margin-left: .5rem;
    }
    .content-subtitle {
        color: var(--text-color-secondary);
        font-size: .95rem;
        margin-left: 1.5rem;
    }
    .content-subnumber {
        font-weight: bold;
    }
    .dashboard-divider {
        margin: 0 auto;
        margin-bottom: 1rem;
        width: 98%;
        height: 1px;
        background-color: var(--border-color);
    }
    @media screen and (max-width: 576px) {
        .dpi-tasks {
            display: none;
        }
        .dashboard-panel-item {
            width: 50%;
        }
    }
</style>