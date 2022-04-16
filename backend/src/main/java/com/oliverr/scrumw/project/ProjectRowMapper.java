package com.oliverr.scrumw.project;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Project(
            resultSet.getLong("id"),
            resultSet.getString("username"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getInt("public") == 1
        );
    }

}
