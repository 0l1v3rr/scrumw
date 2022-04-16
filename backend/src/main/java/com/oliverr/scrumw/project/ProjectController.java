package com.oliverr.scrumw.project;

import com.oliverr.scrumw.user.UserDataAccessService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        if(!userByToken.get().getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        return projectDataAccessService.getLatestThreeProject(username);
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public void addProject(@RequestBody Project project, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        if(!project.getUsername().equalsIgnoreCase(userByToken.get().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        projectDataAccessService.addProject(project);
    }

    @DeleteMapping(value = "{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public void deleteProject(@PathVariable("id") String id, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        var project = projectDataAccessService.getProjectById(Integer.parseInt(id));
        if(project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with this id does not exist.", new RuntimeException());
        }

        if(!project.get().getUsername().equalsIgnoreCase(userByToken.get().getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        projectDataAccessService.deleteProject(Integer.parseInt(id));
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/{projectname}")
    public Project getProjectByUsernameAndProjectName(@PathVariable("username") String username, @PathVariable("projectname") String projectName, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService.getProjectByUsernameAndProjectName(username, projectName);

        if(project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException());
        }

        if(project.get().getIsPublic()) {
            return project.get();
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if(userByToken.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        if(!userByToken.get().getUsername().equalsIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token.", new RuntimeException());
        }

        return project.get();
    }

}
