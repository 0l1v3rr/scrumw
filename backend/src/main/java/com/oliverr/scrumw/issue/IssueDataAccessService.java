package com.oliverr.scrumw.issue;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class IssueDataAccessService implements IssueDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Issue> getIssuesByUsername(String username) {
        var sql = """
                SELECT *
                FROM issues
                WHERE opened_by = ?
                ORDER BY id DESC;
                """;
        return jdbcTemplate.query(sql, new IssueRowMapper(), username);
    }

    @Override
    public List<Issue> getLatestThreeIssues(String username) {
        var sql = """
                SELECT *
                FROM issues
                WHERE opened_by = ?
                ORDER BY opened DESC
                LIMIT 3;
                """;
        return jdbcTemplate.query(sql, new IssueRowMapper(), username);
    }

    @Override
    public List<Issue> getIssuesByProject(String username, String projectName) {
        var sql = """
                SELECT *
                FROM issues
                WHERE project_owner = ? AND project_name = ?
                ORDER BY id DESC;
                """;
        return jdbcTemplate.query(sql, new IssueRowMapper(), username, projectName);
    }

    @Override
    public void addIssue(Issue issue) {
        var sql = """
                INSERT INTO issues(
                    project_owner,
                    project_name,
                    issue_title,
                    issue_description,
                    is_open,
                    opened_by,
                    closed_by,
                    opened,
                    closed
                ) VALUES(
                    ?, ?, ?, ?, ?, ?, ?, ?, ?
                );
                """;
        jdbcTemplate.update(
                sql,
                issue.getProjectOwner(),
                issue.getProjectName(),
                issue.getIssueTitle(),
                issue.getIssueDescription(),
                issue.getIsOpen() ? 1 : 0,
                issue.getOpenedBy(),
                issue.getClosedBy(),
                issue.getOpened(),
                issue.getClosed()
        );
    }

    @Override
    public void deleteIssue(int id) {
        var sql = """
                DELETE FROM issues
                WHERE id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void closeIssue(int id, String closedBy) {
        var sql = """
                UPDATE issues 
                SET is_open = 0, closed_by = ?, closed = ? 
                WHERE id = ?;
                """;
        jdbcTemplate.update(sql, closedBy, LocalDate.now(), id);
    }

    @Override
    public Optional<Issue> getIssueByIssueTitle(String username, String projectName, String issueTitle) {
        var sql = """
                SELECT *
                FROM issues
                WHERE project_owner = ? AND project_name = ? AND issue_title = ?;
                """;
        return jdbcTemplate.query(sql, new IssueRowMapper(), username, projectName, issueTitle).stream().findFirst();
    }

    @Override
    public Optional<Issue> getIssueById(int id) {
        var sql = """
                SELECT *
                FROM issues
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new IssueRowMapper(), id).stream().findFirst();
    }

    @Override
    public int getUsernameIssueCount(String username) {
        return getIssuesByUsername(username).size();
    }

    @Override
    public long getUsernameClosedIssueCount(String username) {
        return getIssuesByUsername(username)
                .stream()
                .filter(issue -> !issue.getIsOpen()).count();
    }

    @Override
    public long getUsernameOpenIssueCount(String username) {
        return getIssuesByUsername(username)
                .stream()
                .filter(Issue::getIsOpen).count();
    }

    @Override
    public int getProjectIssueCount(String username, String projectName) {
        return getIssuesByProject(username, projectName).size();
    }

    @Override
    public long getProjectClosedIssueCount(String username, String projectName) {
        return getIssuesByProject(username, projectName)
                .stream()
                .filter(issue -> !issue.getIsOpen()).count();
    }

    @Override
    public long getProjectOpenIssueCount(String username, String projectName) {
        return getIssuesByProject(username, projectName)
                .stream()
                .filter(Issue::getIsOpen).count();
    }

}
