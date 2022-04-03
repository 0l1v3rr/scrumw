<script>
    import { page } from '$app/stores';
    import { BellIcon, BookmarkIcon } from 'svelte-feather-icons';

    let pageTitle;
    if($page.url.toString().endsWith('/')) pageTitle = "Dashboard";
    else if($page.url.toString().endsWith('/projects')) pageTitle = "Projects";
    else if($page.url.toString().endsWith('/tasks')) pageTitle = "Tasks";
    else if($page.url.toString().endsWith('/settings')) pageTitle = "Settings";

    let messagesCount = 1;
</script>

<header class="header">
    <div class="page-title">{pageTitle}</div>

    <a href="/new" class="btn btn-success new-btn"><BookmarkIcon size="16" /> New Project</a>

    <div class="profile-section">
        <div class="bell-icon">
            { #if messagesCount > 0}
                <div class="notification-dot"></div>
            { /if }
            <BellIcon size="20" />
        </div>

        <div class="profile">
            <img src="https://avatars.githubusercontent.com/u/77695356?v=4" alt="" class="profile-picture">
            <div class="arrow-down"></div>
        </div>
    </div>
</header>

<style>
    .header {
        width: 100vw;
        height: 4rem;
        background-color: var(--background-primary);
        border-bottom: 1px solid var(--border-color);
        padding-left: var(--sidebar-width);
        display: flex;
        align-items: center;
        position: fixed;
        z-index: 1;
    }
    .profile-section {
        display: flex;
        align-items: center;
        border-left: 1px solid var(--border-color);
        height: 100%;
        width: 20vw;
    }
    .page-title {
        color: var(--text-color-primary);
        font-weight: bold;
        font-size: 1.2rem;
        padding: 0 1.5rem;
    }
    .bell-icon {
        border: 1px solid var(--border-color);
        border-radius: .6rem;
        padding: .7rem .9rem;
        line-height: 1;
        margin-left: 1rem;
        color: var(--text-color-secondary);
        cursor: pointer;
        transition: .3s ease-in-out;
        position: relative;
    }
    .bell-icon:hover {
        border-color: var(--border-color-light);
    }
    .bell-icon:hover {
        color: var(--color-primary-light);
    }
    .notification-dot {
        position: absolute;
        width: .4rem;
        height: .4rem;
        background-color: var(--color-primary);
        top: 15%;
        left: 70%;
        border-radius: 50%;
    }
    .profile {
        margin-left: auto;
        margin-right: 1rem;
        border: 1px solid var(--border-color);
        padding: .5rem 1rem;
        border-radius: .6rem;
        cursor: pointer;
        display: flex;
        justify-content: center;
        align-items: center;
        transition: .3s ease-in-out;
    }
    .profile:hover {
        border-color: var(--border-color-light);
    }
    .profile:hover .arrow-down {
        border-color: var(--color-primary);
        border-top-color: transparent;
        border-left-color: transparent;
    }
    .profile-picture {
        width: 1.8rem;
        height: 1.8rem;
        border-radius: 50%;
    }
    .arrow-down {
        width: .6rem;
        height: .6rem;
        border: 2px solid var(--border-color);
        margin-left: .75rem;
        transform: rotate(45deg) translateY(-25%);
        transition: .3s ease-in-out;
        border-top-color: transparent;
        border-left-color: transparent;
    }
    .new-btn {
        margin-left: auto;
        gap: .5rem;
        margin-right: 1rem;
    }

    @media screen and (max-width: 922px) {
        .profile-section {
            width: 24vw;
        }
    }
    @media screen and (max-width: 768px) {
        .profile-section {
            width: 30vw;
        }
    }
    @media screen and (max-width: 576px) {
        .header {
            padding-left: 0;
        }
        .profile-section {
            width: 45vw;
            margin-left: auto;
        }
        .new-btn {
            display: none;
        }
    }
</style>