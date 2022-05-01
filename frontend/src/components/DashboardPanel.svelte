<script>
    import {  FolderMinusIcon, TrelloIcon, AlertCircleIcon } from 'svelte-feather-icons';
    import { onMount } from "svelte";

    let projectCount = 0;
    let privateProjectCount = 0;
    let totalIssues = 0;
    let openIssues = 0;

    const getProjectCount = async (username) => {
        const res = await fetch(`http://localhost:8080/api/v1/projects/${username}/count`);
        const json = await res.json();
        return json.count;
    };

    const getPrivateProjectCount = async (username) => {
        const res = await fetch(`http://localhost:8080/api/v1/projects/${username}/count/private`);
        const json = await res.json();
        return json.count;
    };

    const getTotalIssues = async (username) => {
        const res = await fetch(`http://localhost:8080/api/v1/issues/${username}/count`);
        const json = await res.json();
        return json.count;
    };

    const getOpenIssues = async (username) => {
        const res = await fetch(`http://localhost:8080/api/v1/issues/${username}/count/open`);
        const json = await res.json();
        return json.count;
    };

    onMount(async () => {
        const username = localStorage.getItem('username');

        projectCount = await getProjectCount(username);
        privateProjectCount = await getPrivateProjectCount(username);
        totalIssues = await getTotalIssues(username);
        openIssues = await getOpenIssues(username);
    });
</script>

<div class="dashboard-panel">
    <div class="dashboard-panel-item dpi-projects">
        <div class="dashboard-title">
            <div class="dashboard-icon" style="color: var(--color-warning);"><FolderMinusIcon size="24" /></div>
            <div class="dashboard-title-text">Projects</div>
        </div>
        <div class="dashboard-content">
            <div class="content-number">{projectCount ? projectCount : 0}</div>
            <div class="content-subtitle"><span class="content-subnumber">{privateProjectCount ? privateProjectCount : 0}</span> private</div>
        </div>
    </div>

    <div class="dashboard-panel-item dpi-issues">
        <div class="dashboard-title" style="color: var(--color-danger);">
            <div class="dashboard-icon"><AlertCircleIcon size="24" /></div>
            <div class="dashboard-title-text">Total Issues</div>
        </div>
        <div class="dashboard-content">
            <div class="content-number">{totalIssues ? totalIssues : 0}</div>
            <div class="content-subtitle"><span class="content-subnumber">{openIssues ? openIssues : 0}</span> open</div>
        </div>
    </div>

    <div class="dashboard-panel-item dpi-tasks">
        <div class="dashboard-title">
            <div class="dashboard-icon" style="color: var(--color-success-light);"><TrelloIcon size="24" /></div>
            <div class="dashboard-title-text">Total Scrums</div>
        </div>
        <div class="dashboard-content">
            <div class="content-number">2</div>
            <div class="content-subtitle"><span class="content-subnumber">7</span> completed</div>
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