package com.oliverr.scrumw.issue;

import com.oliverr.scrumw.error.ApiError;
import com.oliverr.scrumw.project.Project;
import com.oliverr.scrumw.project.ProjectDataAccessService;
import com.oliverr.scrumw.security.AuthenticateUser;
import com.oliverr.scrumw.user.UserDataAccessService;
import com.oliverr.scrumw.util.Count;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/issues")
public record IssueController(IssueDataAccessService issueDataAccessService, UserDataAccessService userDataAccessService, ProjectDataAccessService projectDataAccessService, AuthenticateUser authenticateUser) {

    @GetMapping(path = "/user/{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getIssuesByUsername(@PathVariable("username") String username, HttpEntity<byte[]> request) {
        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), username)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issueDataAccessService.getIssuesByUsername(username));
    }

    @GetMapping(path = "{username}/latest")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getLatestIssues(@PathVariable("username") String username, HttpEntity<byte[]> request) {
        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), username)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issueDataAccessService.getLatestThreeIssues(username));
    }

    @GetMapping(path = "{username}/{projectName}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getIssuesByProject(@PathVariable("username") String username, @PathVariable("projectName") String projectName, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService
                .getProjectByUsernameAndProjectName(username, projectName);

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("This project does not exist."));
        }

        if (project.get().getIsPublic()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(issueDataAccessService.getIssuesByProject(username, projectName));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), username)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issueDataAccessService.getIssuesByProject(username, projectName));
    }

    @GetMapping(path = "{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getIssueByTitle(@PathVariable("id") Integer id, HttpEntity<byte[]> request) {
        Optional<Issue> issue = issueDataAccessService.getIssueById(id);
        if(issue.isEmpty()) {
            ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Issue with this id does not exist."));
        }

        Project project = projectDataAccessService.getProjectByUsernameAndProjectName(
                issue.get().getProjectOwner(),
                issue.get().getProjectName()
        ).get();

        if(project.getIsPublic()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(issue.get());
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), issue.get().getOpenedBy())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issue.get());
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public ResponseEntity<Object> addIssue(@RequestBody Issue issue, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService
                .getProjectByUsernameAndProjectName(issue.getProjectOwner(), issue.getProjectName());

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("This project does not exist."));
        }

        if (project.get().getIsPublic()) {
            issueDataAccessService.addIssue(issue);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(issue);
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), project.get().getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        issueDataAccessService.addIssue(issue);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(issue);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteIssue(@PathVariable("id") Integer id, HttpEntity<byte[]> request) {
        Optional<Issue> issue = issueDataAccessService.getIssueById(id);
        if(issue.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Issue with this id does not exist."));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), issue.get().getOpenedBy())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        issueDataAccessService.deleteIssue(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(null);
    }

    @PatchMapping("{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.PATCH)
    public ResponseEntity<Object> closeIssue(@PathVariable("id") Integer id, HttpEntity<byte[]> request) {
        Optional<Issue> issue = issueDataAccessService.getIssueById(id);
        if(issue.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Issue with this id does not exist."));
        }

        if(!issue.get().getIsOpen()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("This issue is already closed."));
        }

        if(!authenticateUser.withToken(request.getHeaders().getFirst("token"), issue.get().getOpenedBy())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        issueDataAccessService.closeIssue(id, issue.get().getOpenedBy());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(issueDataAccessService.getIssueById(id));
    }

    @GetMapping("{username}/count")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getUsernameIssueCount(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Count(issueDataAccessService.getUsernameIssueCount(username)));
    }

    @GetMapping("{username}/count/closed")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getUsernameClosedIssueCount(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Count(issueDataAccessService.getUsernameClosedIssueCount(username)));
    }

    @GetMapping("{username}/count/open")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getUsernameOpenIssueCount(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Count(issueDataAccessService.getUsernameOpenIssueCount(username)));
    }

}
