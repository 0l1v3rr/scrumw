package com.oliverr.scrumw.scrum;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Scrum {

    private Long id;
    private String projectOwner;
    private String projectName;
    private String title;
    private String description;
    private ScrumStatus status;
    private String createdBy;
    private LocalDate updated;

}
