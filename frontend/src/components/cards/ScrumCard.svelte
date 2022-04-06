<script>
    export let project;
    export let title;
    export let description;
    export let approvedBy = "";
    export let assignedBy = "";
</script>

<div class="scrum-card">
    <a href="/{project}" class="scrum-card-project">{ project }</a>
    <div class="scrum-card-title">{ title }</div>
    <div class="scrum-card-description">{ description }</div>

    { #if assignedBy != "" || approvedBy != "" }
    <div class="scrum-card-footer">
        <div class="footer-text">
            { #if assignedBy != "" }
                <div>Sent by <a href="/{assignedBy}" class="footer-text-bold">{ assignedBy }</a>.</div>
            { /if }
            { #if approvedBy != "" }
                <div>Approved by <a href="/{approvedBy}" class="footer-text-bold">{ approvedBy }</a>.</div>
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
        border: 1px solid var(--border-color);
    }
    .scrum-card-project {
        margin-bottom: .5rem;
        color: var(--link-color);
        font-size: 1rem;
        transition: .3s ease-in-out;
        display: block;
    }
    .scrum-card-project:hover {
        color: var(--color-primary-light);
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
        color: var(--text-color-secondary);
    }
    .footer-text-bold:hover {
        text-decoration: underline;
    }
</style>