package com.oliverr.scrumw.scrum;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ScrumDataAccessService implements ScrumDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Scrum> getScrumsByUsername(String username) {
        var query = """
                SELECT *
                FROM scrums
                WHERE created_by = ?
                ORDER BY updated DESC;
                """;
        return jdbcTemplate.query(query, new ScrumRowMapper(), username);
    }

    @Override
    public List<Scrum> getScrumsByProject(String username, String projectName) {
        var query = """
                SELECT *
                FROM scrums
                WHERE project_owner = ? AND project_name = ?
                ORDER BY updated DESC;
                """;
        return jdbcTemplate.query(query, new ScrumRowMapper(), username, projectName);
    }

    @Override
    public void addScrum(Scrum scrum) {
        var query = """
                INSERT INTO scrums(project_owner, project_name, title, description, status, created_by, updated)
                VALUES(?, ?, ?, ?, ?, ?, ?);
                """;

        String status = "";
        if(scrum.getStatus() == ScrumStatus.IN_PROGRESS) status = "in-progress";
        else if(scrum.getStatus() == ScrumStatus.DONE) status = "done";
        else status = "to-do";

        jdbcTemplate.update(
                query,
                scrum.getProjectOwner(),
                scrum.getProjectName(),
                scrum.getTitle(),
                scrum.getDescription(),
                status,
                scrum.getCreatedBy(),
                LocalDate.now()
        );
    }

    @Override
    public void deleteScrum(int id) {
        var query = """
                DELETE FROM scrums
                WHERE id = ?;
                """;
        jdbcTemplate.update(query, id);
    }

    @Override
    public void changeScrumStatus(int id, ScrumStatus status) {
        var query = """
                UPDATE scrums
                SET status = ?
                WHERE id = ?;
                """;

        updateScrum(id);
        jdbcTemplate.update(query, status.toString().replaceAll("_", "-").toLowerCase(), id);
    }

    @Override
    public Optional<Scrum> getScrumById(int id) {
        var query = """
                SELECT *
                FROM scrums
                WHERE id = ?;
                """;
        return jdbcTemplate.query(query, new ScrumRowMapper(), id).stream().findFirst();
    }

    @Override
    public void updateScrum(int id) {
        LocalDate date = LocalDate.now();
        var query = """
                UPDATE scrums
                SET updated = ?
                WHERE id = ?;
                """;
        jdbcTemplate.update(query, date, id);
    }

    @Override
    public int getScrumCountByProject(String projectOwner, String projectName) {
        return Integer.parseInt(getScrumsByProject(projectOwner, projectName).stream().count() + "");
    }

    @Override
    public int getScrumCountByUser(String username) {
        return Integer.parseInt(getScrumsByUsername(username).stream().count() + "");
    }

}
