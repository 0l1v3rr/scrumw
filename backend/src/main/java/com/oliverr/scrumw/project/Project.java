package com.oliverr.scrumw.project;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Project {

    private Long id;
    private String username;
    private String projectName;
    private String projectDescription;
    private Boolean isPublic;
    private LocalDate created;

    public Project(String username, String projectName, String projectDescription, Boolean isPublic, LocalDate created) {
        this.username = username;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.isPublic = isPublic;
        this.created = created;
    }

}
