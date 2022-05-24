package com.oliverr.scrumw.project;

import com.oliverr.scrumw.error.ApiError;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/projects")
public record ProjectController(ProjectDataAccessService projectDataAccessService, UserDataAccessService userDataAccessService, AuthenticateUser authenticateUser) {

    @GetMapping(path = "{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getProjectsByUsername(@PathVariable("username") String username, HttpEntity<byte[]> request) {
        String token = request.getHeaders().getFirst("token");
        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(projectDataAccessService.getPublicProjectsByUsername(username));
        }

        var userByToken = userDataAccessService.getUserByToken(token);
        if (userByToken.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(projectDataAccessService.getPublicProjectsByUsername(username));
        }

        if (!userByToken.get().getUsername().equalsIgnoreCase(username)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(projectDataAccessService.getPublicProjectsByUsername(username));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectDataAccessService.getProjectsByUsername(username));
    }

    @GetMapping(path = "{username}/latest")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getLatestThreeProjects(@PathVariable("username") String username, HttpEntity<byte[]> request) {
        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), username)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectDataAccessService.getLatestThreeProject(username));
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public ResponseEntity<Object> addProject(@RequestBody Project project, HttpEntity<byte[]> request) {
        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), project.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        Optional<Project> currentProject = projectDataAccessService
                .getProjectByUsernameAndProjectName(project.getUsername(), project.getProjectName());

        if (currentProject.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ApiError("Project with this name already exist."));
        }

        projectDataAccessService.addProject(project);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(project);
    }

    @DeleteMapping(value = "{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteProject(@PathVariable("id") Integer id, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService.getProjectById(id);

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Project with this ID does not exist."));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), project.get().getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        projectDataAccessService.deleteProject(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(null);
    }

    @PatchMapping(value = "{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.PATCH)
    public ResponseEntity<ApiError> changeProjectVisibility(@PathVariable("id") Integer id, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService.getProjectById(id);

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Project with this ID does not exist."));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), project.get().getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        projectDataAccessService.changeVisibility(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(null);
    }

    @PutMapping(value = "{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.PUT)
    public ResponseEntity<Object> updateProject(@PathVariable("id") Integer id, @RequestBody Project newProject, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService.getProjectById(id);

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Project with this ID does not exist."));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), project.get().getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        projectDataAccessService.updateProject(newProject, id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(newProject);
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/{projectName}")
    public ResponseEntity<Object> getProjectByUsernameAndProjectName(@PathVariable("username") String username, @PathVariable("projectName") String projectName, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService.getProjectByUsernameAndProjectName(username, projectName);

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Project with this ID does not exist."));
        }

        if (project.get().getIsPublic()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(project.get());
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), project.get().getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(project.get());
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/count")
    public ResponseEntity<Object> getProjectCountByUsername(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Count(projectDataAccessService.getProjectCount(username)));
    }

    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "{username}/count/private")
    public ResponseEntity<Object> getPrivateProjectCountByUsername(@PathVariable("username") String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Count(projectDataAccessService.getPrivateProjectCount(username)));
    }

}
