package com.oliverr.scrumw.scrum;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ScrumRowMapper implements RowMapper<Scrum> {

    @Override
    public Scrum mapRow(ResultSet rs, int i) throws SQLException {
        ScrumStatus status = null;
        String statusStr = rs.getString("status");

        if(statusStr.equalsIgnoreCase("to-do"))
            status = ScrumStatus.TO_DO;
        else if(statusStr.equalsIgnoreCase("in-progress"))
            status = ScrumStatus.IN_PROGRESS;
        else if(statusStr.equalsIgnoreCase("done"))
            status = ScrumStatus.DONE;

        return new Scrum(
                rs.getLong("id"),
                rs.getString("project_owner"),
                rs.getString("project_name"),
                rs.getString("title"),
                rs.getString("description"),
                status,
                rs.getString("created_by"),
                LocalDate.parse(rs.getString("updated"))
        );
    }

}
