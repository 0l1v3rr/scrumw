<script>
    import Header from "../components/Header.svelte";
    import Sidebar from "../components/Sidebar.svelte";
</script>

<div>
    <Sidebar />
    <Header />
    <slot></slot>
</div>