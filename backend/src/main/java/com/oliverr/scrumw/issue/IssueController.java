package com.oliverr.scrumw.issue;

import com.oliverr.scrumw.project.ProjectDataAccessService;
import com.oliverr.scrumw.user.UserDataAccessService;
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

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        if(!userByToken.get().getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return issueDataAccessService.getLatestThreeIssues(username);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(path = "{username}/{projectName}")
    public List<Issue> getIssuesByProject(@PathVariable("username") String username, @PathVariable("projectName") String projectName, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService.getProjectByUsernameAndProjectName(username, projectName);
        if(project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException());
        }

        if(project.get().getIsPublic()) {
            return issueDataAccessService.getIssuesByProject(username, projectName);
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        if(!userByToken.get().getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return issueDataAccessService.getIssuesByProject(username, projectName);
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public void addIssue(@RequestBody Issue issue, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService.getProjectByUsernameAndProjectName(
                issue.getProjectOwner(),
                issue.getProjectName()
        );

        if(project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException());
        }

        if(project.get().getIsPublic()) {
            issueDataAccessService.addIssue(issue);
            return;
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        issueDataAccessService.addIssue(issue);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public void deleteIssue(@PathVariable("id") String id, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        var issue = issueDataAccessService.getIssueById(Integer.parseInt(id));
        if(issue.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue with this ID does not exist.", new RuntimeException());
        }

        if(!issue.get().getOpenedBy().equalsIgnoreCase(userByToken.get().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete it.", new RuntimeException());
        }

        issueDataAccessService.deleteIssue(Integer.parseInt(id));
    }

}
