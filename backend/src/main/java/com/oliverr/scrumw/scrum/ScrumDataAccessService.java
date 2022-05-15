package com.oliverr.scrumw.scrum;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
                scrum.getUpdated()
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
        String statusStr = "";
        if(status == ScrumStatus.IN_PROGRESS) statusStr = "in-progress";
        else if(status == ScrumStatus.DONE) statusStr = "done";
        else statusStr = "to-do";

        var query = """
                UPDATE scrums
                SET status = ?
                WHERE id = ?;
                """;

        jdbcTemplate.update(query, statusStr, id);
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

}
