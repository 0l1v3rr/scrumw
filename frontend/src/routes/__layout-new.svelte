<script>
    import Header from "../components/Header.svelte";
    import Sidebar from "../components/Sidebar.svelte";
    import RightSidebar from "../components/RightSidebar.svelte";
</script>

<Sidebar />
<Header />
<div class="wrapper">
    <div class="slot">
        <slot></slot>
    </div>
    <RightSidebar />
</div>

<style>
    .wrapper {
        display: flex;
        align-items: start;
        padding-top: 4rem;
    }
    .slot {
        padding-left: var(--sidebar-width);
        padding-right: 22vw;
        width: 100%;
    }

    @media screen and (max-width: 922px) {
        .slot {
            padding-right: 0;
        }
    }
    @media screen and (max-width: 576px) {
        .slot {
            padding-left: 0;
        }
    }
</style>