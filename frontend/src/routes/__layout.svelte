<script>
    import Header from "../components/Header.svelte";
    import Sidebar from "../components/Sidebar.svelte";
    import RightSidebar from "../components/RightSidebar.svelte";
</script>

<div>
    <Sidebar />
    <Header />
    <slot></slot>
    <RightSidebar />
</div>