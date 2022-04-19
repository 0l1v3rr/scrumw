package com.oliverr.scrumw.issue;

import com.oliverr.scrumw.project.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IssueRowMapper implements RowMapper<Issue> {

    @Override
    public Issue mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Issue(
            resultSet.getLong("id"),
            resultSet.getString("project_owner"),
            resultSet.getString("project_name"),
            resultSet.getString("issue_title"),
            resultSet.getString("issue_description"),
            resultSet.getInt("is_open") == 1,
            resultSet.getString("opened_by"),
            resultSet.getString("closed_by"),
            LocalDate.parse(resultSet.getString("opened")),
            LocalDate.parse(resultSet.getString("closed"))
        );
    }

}
