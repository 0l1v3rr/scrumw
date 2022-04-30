package com.oliverr.scrumw.project;

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
@RequestMapping(path = "/api/v1/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectDataAccessService projectDataAccessService;
    private final UserDataAccessService userDataAccessService;

    @GetMapping(path = "{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public List<Project> getProjectsByUsername(@PathVariable("username") String username, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            return projectDataAccessService.getPublicProjectsByUsername(username);
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            return projectDataAccessService.getPublicProjectsByUsername(username);
        }

        if(!userByToken.get().getUsername().equalsIgnoreCase(username)) {
            return projectDataAccessService.getPublicProjectsByUsername(username);
        }

        return projectDataAccessService.getProjectsByUsername(username);
    }

    @GetMapping(path = "{username}/latest")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public List<Project> getLatestThreeProjects(@PathVariable("username") String username, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return projectDataAccessService.getLatestThreeProject(username);
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public void addProject(@RequestBody Project project, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!project.getUsername().equalsIgnoreCase(userByToken.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        var currentProject = projectDataAccessService
                .getProjectByUsernameAndProjectName(project.getUsername(), project.getProjectName());
        if(currentProject.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Project with this name already exists.", new RuntimeException());
        }

        projectDataAccessService.addProject(project);
    }

    @DeleteMapping(value = "{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public void deleteProject(@PathVariable("id") String id, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        var project = projectDataAccessService
                .getProjectById(Integer.parseInt(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with this id does not exist.", new RuntimeException()));

        if(!project.getUsername().equalsIgnoreCase(userByToken.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        projectDataAccessService.deleteProject(Integer.parseInt(id));
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/{projectName}")
    public Project getProjectByUsernameAndProjectName(@PathVariable("username") String username, @PathVariable("projectName") String projectName, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService
                .getProjectByUsernameAndProjectName(username, projectName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException()));

        if(project.getIsPublic()) {
            return project;
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        return project;
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/count")
    public Count getProjectCountByUsername(@PathVariable("username") String username) {
        return new Count(projectDataAccessService.getProjectCount(username));
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/count/private")
    public Count getPrivateProjectCountByUsername(@PathVariable("username") String username) {
        return new Count(projectDataAccessService.getPrivateProjectCount(username));
    }

}
