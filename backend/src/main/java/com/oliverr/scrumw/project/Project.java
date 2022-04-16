package com.oliverr.scrumw.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    private Long id;
    private String username;
    private String projectName;
    private String projectDescription;
    private Boolean isPublic;

    public Project(String username, String projectName, String projectDescription, Boolean isPublic) {
        this.username = username;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.isPublic = isPublic;
    }

}
