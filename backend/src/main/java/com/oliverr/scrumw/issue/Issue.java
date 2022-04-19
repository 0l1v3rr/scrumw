package com.oliverr.scrumw.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Issue {

    private Long id;
    private String projectOwner;
    private String projectName;
    private String issueTitle;
    private String issueDescription;
    private Boolean isOpen;
    private String openedBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String closedBy;
    private LocalDate opened;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate closed;

}
