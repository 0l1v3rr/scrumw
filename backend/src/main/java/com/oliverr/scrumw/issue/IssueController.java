package com.oliverr.scrumw.issue;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/issues")
@AllArgsConstructor
public class IssueController {

    private final IssueDataAccessService issueDataAccessService;

}
