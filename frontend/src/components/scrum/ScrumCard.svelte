<script>
    export let project;
    export let title;
    export let description;
    export let approvedBy = "";
    export let assignedBy = "";
</script>

<div class="scrum-card">
    <div class="scrum-card-project">{ project }</div>
    <div class="scrum-card-title">{ title }</div>
    <div class="scrum-card-description">{ description }</div>

    { #if assignedBy != "" || approvedBy != "" }
    <div class="scrum-card-footer">
        <div class="footer-text">
            { #if assignedBy != "" }
                <div>Sent by <span class="footer-text-bold">{ assignedBy }</span>.</div>
            { /if }
            { #if approvedBy != "" }
                <div>Approved by <span class="footer-text-bold">{ approvedBy }</span>.</div>
            { /if }
        </div>
    </div>
    { /if }
    
</div>

<style>
    .scrum-card {
        margin-top: 1rem;
        padding: 1rem;
        background-color: var(--background-primary);
        border-radius: .5rem;
    }
    .scrum-card-project {
        color: var(--text-color-primary);
        margin-bottom: .5rem;
    }
    .scrum-card-title {
        color: var(--text-color-primary);
        font-size: 1rem;
        font-weight: bold;
        margin-bottom: .5rem;
    }
    .scrum-card-description {
        color: var(--text-color-secondary);
        font-size: .95rem;
        text-align: justify;
    }
    .scrum-card-footer {
        margin-top: 1rem;
        padding: .5rem 0;
        padding-bottom: 0;
        border-top: 1px solid var(--border-color);
        display: flex;
        align-items: center;
    }
    .footer-text {
        color: var(--text-color-secondary);
        font-size: .95rem;
    }
    .footer-text-bold {
        font-weight: bold;
    }
</style>