<script>
    import { onMount } from "svelte";
    import { BellIcon, BookmarkIcon, SettingsIcon, UserIcon, LogOutIcon } from 'svelte-feather-icons';

    let messagesCount = 1;

    let userBtn;
    let isActive = false;
    onMount(() => {
        userBtn.addEventListener('click', () => {
            if(isActive) isActive = false;
            else isActive = true;
        });
    });
</script>

<header class="header">
    <div class="page-title">scrumw</div>

    <a href="/new/project" class="btn btn-success new-btn"><BookmarkIcon size="16" /> New Project</a>

    <div class="profile-section">
        <a href="/notifications" class="bell-icon">
            { #if messagesCount > 0}
                <div class="notification-dot"></div>
            { /if }
            <BellIcon size="20" />
        </a>

        <div class="profile-container">
            <button class="profile {isActive ? 'active' : ''}" bind:this={userBtn}>
                <img src="https://avatars.githubusercontent.com/u/77695356?v=4" alt="" class="profile-picture">
                <div class="arrow-down"></div>
            </button>

            <div class="profile-dropdown {isActive ? 'active' : ''}">
                <a href="/profile" class="profile-dropdown-item">
                    <UserIcon size="16" />
                    Profile
                </a>
                <a href="/settings" class="profile-dropdown-item">
                    <SettingsIcon size="16" />
                    Settings
                </a>
                <a href="/" class="profile-dropdown-item">
                    <LogOutIcon size="16" />
                    Log Out
                </a>
            </div>
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
        width: 22vw;
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
        border: 1px solid var(--border-color);
        padding: .5rem 1rem;
        border-radius: .6rem;
        cursor: pointer;
        display: flex;
        justify-content: center;
        align-items: center;
        transition: .3s ease-in-out;
        background-color: var(--background-primary);
    }
    .profile:hover {
        border-color: var(--border-color-light);
    }
    .profile-container {
        margin-left: auto;
        margin-right: 1rem;
        position: relative;
    }
    .profile:hover .arrow-down {
        border-color: var(--color-primary);
        border-top-color: transparent;
        border-left-color: transparent;
    }
    .profile.active {
        border-color: var(--border-color-light);
    }
    .profile.active .arrow-down {
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
    .profile-dropdown {
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        border-radius: .4rem;
        position: absolute;
        top: 110%;
        left: -35%;
        z-index: 4;
        box-shadow: 0 0 .5rem rgba(0, 0, 0, .4);
        user-select: none;
        pointer-events: none;
        opacity: 0;
    }
    .profile-dropdown.active {
        opacity: 1;
        user-select: all;
        pointer-events: all;
    }
    .profile-dropdown::before {
        content: '';
        top: 0%;
        left: 50%;
        width: .6rem;
        height: .6rem;
        position: absolute;
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        border-bottom-color: transparent;
        border-right-color: transparent;
        z-index: 3;
        transform: translate(-50%, -40%) rotate(45deg);
    }
    .profile-dropdown-item {
        padding: .7rem 1.2rem;
        border-bottom: 1px solid var(--border-color);
        display: flex;
        justify-content: center;
        align-items: center;
        gap: .5rem;
        color: var(--text-color-secondary);
        transition: .3s ease-in-out;
    }
    .profile-dropdown-item:hover {
        color: var(--color-primary-light);
    }
    .profile-dropdown-item:last-child {
        border-bottom: 0;
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