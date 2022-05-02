<script>
    import NotFound from "../components/cards/NotFound.svelte";
</script>

<div class="error">
    <NotFound backToHomeBtn={true} searchQuery="this URL" />
</div>

<style>
    .error {
        padding: 2rem;
    }
</style>