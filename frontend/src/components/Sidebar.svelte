<script>
    import { page } from '$app/stores';
    import { LayoutIcon, FolderMinusIcon, ServerIcon, SettingsIcon, LogOutIcon } from 'svelte-feather-icons';
</script>

<aside class="sidebar">
    <div class="logo">
        <div class="logo-container">
            <div class="logo-img">SW.</div>
        </div>
    </div>
    <a href="/" class="sidebar-item {$page.url.toString().endsWith('/') ? 'active' : ''}">
        <LayoutIcon size="24" />
        <div class="sidebar-item-hover">Dashboard</div>
    </a>

    <div class="sidebar-divider"></div>

    <a href="/projects" class="sidebar-item {$page.url.toString().endsWith('/projects') ? 'active' : ''}">
        <FolderMinusIcon size="24" />
        <div class="sidebar-item-hover">Projects</div>
    </a>

    <a href="todo" class="sidebar-item {$page.url.toString().endsWith('/todo') ? 'active' : ''}">
        <ServerIcon size="24" />
        <div class="sidebar-item-hover">To-Do</div>
    </a>

    <a href="settings" class="sidebar-item settings-icon {$page.url.toString().endsWith('/settings') ? 'active' : ''}">
        <SettingsIcon size="24" />
        <div class="sidebar-item-hover">Settings</div>
    </a>

    <a href="/" class="sidebar-item">
        <LogOutIcon size="24" />
        <div class="sidebar-item-hover">Log Out</div>
    </a>
</aside>

<style>
    .sidebar {
        background-color: var(--background-primary);
        border-right: 1px solid var(--border-color);
        display: flex;
        align-items: center;
        flex-direction: column;
        padding: 1rem 0;
        height: 100vh;
        position: fixed;
        width: var(--sidebar-width);
        z-index: 2;
    }

    .logo-container {
        padding: 1rem .8rem;
        background-color: var(--color-primary);
        border-radius: .5rem;
        cursor: pointer;
    }
    .logo-img {
        color: white;
        font-weight: bold;
        font-size: 1rem;
    }
    .sidebar-item {
        cursor: pointer;
        color: var(--text-color-secondary);
        margin-top: 1.2rem;
        transition: .3s ease-in-out;
        position: relative;
    }
    .sidebar-item.active {
        color: var(--color-primary);
    }
    .sidebar-item:hover {
        color: var(--color-primary-light);
    }
    .sidebar-item:hover > .sidebar-item-hover {
        opacity: 1;
    }
    .sidebar-divider {
        width: 50%;
        height: 2px;
        background-color: var(--border-color);
        margin-top: 1.2rem;
    }
    .settings-icon {
        margin-top: auto;
    }
    .sidebar-item-hover {
        position: absolute;
        z-index: 2;
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        color: var(--text-color-primary) !important;
        padding: .5rem;
        padding-left: .6rem;
        border-radius: .5rem;
        top: 0;
        transform: translateY(-15%);
        left: calc(var(--sidebar-width) / 2);
        pointer-events: none;
        user-select: none;
        font-size: .95rem;
        box-shadow: 0 0 .5rem rgba(0, 0, 0, .3);
        opacity: 0;
        transition: .1s ease-in-out;
        white-space: nowrap;
    }
    .sidebar-item-hover::before {
        content: '';
        top: 50%;
        left: 0;
        width: .6rem;
        height: .6rem;
        position: absolute;
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        border-top-color: transparent;
        border-right-color: transparent;
        z-index: 3;
        transform: translate(-50%, -40%) rotate(45deg);
    }

    @media screen and (max-width: 576px) {
        .sidebar {
            flex-direction: row;
            height: var(--sidebar-width);
            width: 100vw;
            border-right: 0;
            border-top: 1px solid var(--border-color);
            bottom: 0;
            gap: 1.5rem;
            padding: 0 1.5rem;
        }
        .logo {
            display: none;
        }
        .sidebar-divider {
            display: none;
        }
        .settings-icon {
            margin-top: 0;
            margin-left: auto;
        }
        .sidebar-item {
            margin-top: 0;
        }
        .sidebar-item-hover {
            display: none;
        }
    }
</style>