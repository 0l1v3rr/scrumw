<script>
    import ProjectCard from "../components/cards/ProjectCard.svelte";
</script>

<svelte:head>
    <title>Projects | scrumw</title>
</svelte:head>

<main>
    <div class="projects-header">
        <div class="project-title">Projects</div>
    </div>

    <div class="projects-container">
        <ProjectCard 
            owner="0l1v3rr"
            name="test-project"
            description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Repudiandae, iure. Similique odio totam consectetur maiores perspiciatis? Voluptas earum quasi expedita!"
            isPublic={false}
        />

        <ProjectCard 
            owner="D0e"
            name="scrum-project"
            description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Repudiandae, iure. Similique odio totam consectetur maiores perspiciatis?"
            issueCount={12}
        />

        <ProjectCard 
            owner="0l1v3rr"
            name="scrum"
            description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Repudiandae, iure. Similique odio totam consectetur maiores perspiciatis?"
            scrumCount={6}
        />

        <ProjectCard 
            owner="J0hn"
            name="test"
            description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Repudiandae, iure. Similique odio totam consectetur maiores perspiciatis?"
        />
    </div>
</main>

<style>
    .projects-header {
        padding: 0 1rem;
    }
    .project-title {
        color: var(--text-color-primary);
        font-weight: bold;
        font-size: 1.1rem;
    }
    .projects-container {
        background-color: var(--background-secondary);
        padding: 1rem;
        gap: 1rem;
        height: 100%;
        display: grid;
        grid-template-columns: repeat(2, 1fr);
    }
    @media screen and (max-width: 768px) {
        .projects-container {
            grid-template-columns: 1fr;
        }
    }
</style>