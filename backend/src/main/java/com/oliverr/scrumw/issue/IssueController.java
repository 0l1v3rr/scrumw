package com.oliverr.scrumw.issue;

import com.oliverr.scrumw.project.ProjectDataAccessService;
import com.oliverr.scrumw.user.UserDataAccessService;
import com.oliverr.scrumw.util.Count;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/issues")
@AllArgsConstructor
public class IssueController {

    private final IssueDataAccessService issueDataAccessService;
    private final UserDataAccessService userDataAccessService;
    private final ProjectDataAccessService projectDataAccessService;

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "{username}/latest")
    public List<Issue> getLatestIssues(@PathVariable("username") String username, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return issueDataAccessService.getLatestThreeIssues(username);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "{username}/{projectName}")
    public List<Issue> getIssuesByProject(@PathVariable("username") String username, @PathVariable("projectName") String projectName, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService
                .getProjectByUsernameAndProjectName(username, projectName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException()));

        if(project.getIsPublic()) {
            return issueDataAccessService.getIssuesByProject(username, projectName);
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return issueDataAccessService.getIssuesByProject(username, projectName);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "{id}")
    public Issue getIssueByTitle(@PathVariable("id") Integer id, HttpEntity<byte[]> requestEntity) {
        var issue = issueDataAccessService
                .getIssueById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue with this name does not exist."));

        var project = projectDataAccessService
                .getProjectByUsernameAndProjectName(issue.getProjectOwner(), issue.getProjectName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException()));

        if(project.getIsPublic()) {
            return issue;
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(issue.getOpenedBy())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return issue;
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public void addIssue(@RequestBody Issue issue, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService
                .getProjectByUsernameAndProjectName(issue.getProjectOwner(), issue.getProjectName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException()));

        if(project.getIsPublic()) {
            issueDataAccessService.addIssue(issue);
            return;
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        issueDataAccessService.addIssue(issue);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public void deleteIssue(@PathVariable("id") String id, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        var issue = issueDataAccessService
                .getIssueById(Integer.parseInt(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue with this ID does not exist.", new RuntimeException()));

        if(!issue.getOpenedBy().equalsIgnoreCase(userByToken.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete it.", new RuntimeException());
        }

        issueDataAccessService.deleteIssue(Integer.parseInt(id));
    }

    @GetMapping("{username}/count")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public Count getUsernameIssueCount(@PathVariable("username") String username) {
        var user = userDataAccessService
                .findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this username does not exist.", new RuntimeException()));
        return new Count(issueDataAccessService.getUsernameIssueCount(username));
    }

    @GetMapping("{username}/count/closed")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public Count getUsernameClosedIssueCount(@PathVariable("username") String username) {
        var user = userDataAccessService
                .findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this username does not exist.", new RuntimeException()));
        return new Count(issueDataAccessService.getUsernameClosedIssueCount(username));
    }

    @GetMapping("{username}/count/open")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public Count getUsernameOpenIssueCount(@PathVariable("username") String username) {
        var user = userDataAccessService
                .findUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this username does not exist.", new RuntimeException()));
        return new Count(issueDataAccessService.getUsernameOpenIssueCount(username));
    }

}
