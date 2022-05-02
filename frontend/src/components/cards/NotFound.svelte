<script>
    export let searchQuery;
    export let backToHomeBtn = false;
</script>

<div class="not-found-card">
    <h1 class="not-found-title">OOPS!</h1>
    <p class="not-found-description-main">There's <span class="nothing-span">NOTHING</span> here...</p>
    <p class="not-found-description-sub">No result for <b>{searchQuery}</b>.</p>
    {#if backToHomeBtn}
        <a href="/" class="btn btn-danger" style="margin-top: 1rem;">Back to Home</a>
    {/if}
</div>

<style>
    .not-found-card {
        background-color: var(--background-primary);
        border: 1px solid var(--border-color);
        padding: 1rem;
        border-radius: .4rem;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        gap: .35rem;
        width: 100%;
    }
    .not-found-title {
        font-weight: 700;
        font-size: 2.5rem;
        color: var(--color-danger);
    }
    .nothing-span {
        color: var(--color-danger);
        font-weight: bold;
        text-decoration: underline;
        letter-spacing: 1px;
    }
    .not-found-description-main {
        font-size: 1.3rem;
    }
    .not-found-description-sub {
        color: var(--text-color-secondary);
    }
</style>