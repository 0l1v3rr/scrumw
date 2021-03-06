package com.oliverr.scrumw.project;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProjectDataAccessService implements ProjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Project> getProjectsByUsername(String username) {
        var sql = """
                SELECT *
                FROM projects
                WHERE username = ?
                ORDER BY id DESC;
                """;
        return jdbcTemplate.query(sql, new ProjectRowMapper(), username);
    }

    @Override
    public List<Project> getLatestThreeProject(String username) {
        var sql = """
                SELECT *
                FROM projects
                WHERE username = ?
                ORDER BY id DESC
                LIMIT 3;
                """;
        return jdbcTemplate.query(sql, new ProjectRowMapper(), username);
    }

    @Override
    public List<Project> getPublicProjectsByUsername(String username) {
        var sql = """
                SELECT *
                FROM projects
                WHERE username = ? AND public = 1
                ORDER BY id DESC;
                """;
        return jdbcTemplate.query(sql, new ProjectRowMapper(), username);
    }

    @Override
    public List<Project> getPrivateProjectsByUsername(String username) {
        var sql = """
                SELECT *
                FROM projects
                WHERE username = ? AND public <> 1
                ORDER BY id DESC;
                """;
        return jdbcTemplate.query(sql, new ProjectRowMapper(), username);
    }

    @Override
    public void addProject(Project project) {
        var sql = """
                INSERT INTO projects(username, name, description, public, created)
                VALUES (?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(
                sql,
                project.getUsername(),
                project.getProjectName(),
                project.getProjectDescription(),
                project.getIsPublic() ? 1 : 0,
                LocalDate.now()
        );
    }

    @Override
    public void updateProject(Project project, int id) {
        var sql = """
                UPDATE projects
                SET username = ?, name = ?, description = ?, public = ?
                WHERE id = ?;
                """;
        jdbcTemplate.update(
                sql,
                project.getUsername(),
                project.getProjectName(),
                project.getProjectDescription(),
                project.getIsPublic() ? 1 : 0,
                id
        );
    }

    @Override
    public void deleteProject(int id) {
        var sql = """
                DELETE 
                FROM projects
                WHERE id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Project> getProjectById(int id) {
        var sql = """
                SELECT *
                FROM projects
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new ProjectRowMapper(), id).stream().findFirst();
    }

    @Override
    public Optional<Project> getProjectByUsernameAndProjectName(String username, String projectName) {
        var sql = """
                SELECT *
                FROM projects
                WHERE username = ? AND name = ?;
                """;
        return jdbcTemplate.query(sql, new ProjectRowMapper(), username, projectName).stream().findFirst();
    }

    @Override
    public int getProjectCount(String username) {
        var res = getProjectsByUsername(username);
        return Integer.parseInt(res.size()+"");
    }

    @Override
    public int getPrivateProjectCount(String username) {
        var res = getPrivateProjectsByUsername(username);
        return Integer.parseInt(res.size()+"");
    }

    @Override
    public void changeVisibility(int id) {
        var sql = """
                UPDATE projects 
                SET public = NOT public 
                WHERE id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

}
