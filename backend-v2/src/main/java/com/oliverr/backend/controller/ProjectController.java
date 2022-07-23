package com.oliverr.backend.controller;

import com.oliverr.backend.exception.ForbiddenException;
import com.oliverr.backend.model.Project;
import com.oliverr.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> saveProject(@RequestBody Project project, Principal principal) {
        project.setOwner(principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.saveProject(project));
    }

    @GetMapping("{username}")
    public ResponseEntity<List<Project>> getAllProjectsByUsername(@PathVariable("username") String username,
                                                                  Principal principal) {
        if(!username.equalsIgnoreCase(principal.getName())) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(projectService.getPublicProjectsByUsername(username));
        }

        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProjectsByUsername(username));
    }

    @GetMapping("{username}/latest")
    public ResponseEntity<List<Project>> getLatestProjectsByUsername(@PathVariable("username") String username, Principal principal, @RequestParam(required = false, name = "limit") Integer limit) {
        if(!username.equalsIgnoreCase(principal.getName())) {
            throw new ForbiddenException();
        }

        if(limit == null) limit = 3;

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(projectService.getLatestProjectsByUsername(username, limit));
    }

    @PutMapping("{projectId}")
    public ResponseEntity<Project> updateProject(@RequestBody Project project,
                                                 @PathVariable("projectId") Long id,
                                                 Principal principal) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(projectService.updateProject(project, id, principal.getName()));
    }

    @PatchMapping("{projectId}")
    public ResponseEntity<Project> changeProjectVisibility(@PathVariable("projectId") Long id,
                                                           Principal principal) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(projectService.changeVisibility(id, principal.getName()));
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Long id,
                                           Principal principal) {
        projectService.deleteProject(id, principal.getName());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

}
