package com.oliverr.scrumw.scrum;

import com.oliverr.scrumw.error.ApiError;
import com.oliverr.scrumw.project.Project;
import com.oliverr.scrumw.project.ProjectDataAccessService;
import com.oliverr.scrumw.security.AuthenticateUser;
import com.oliverr.scrumw.user.UserDataAccessService;
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
@RequestMapping("/api/v1/scrum")
public record ScrumController(UserDataAccessService userDataAccessService, ScrumDataAccessService scrumDataAccessService, ProjectDataAccessService projectDataAccessService, AuthenticateUser authenticateUser) {

    @GetMapping("{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getScrumsByUsername(@PathVariable("username") String username, HttpEntity<byte[]> request) {
        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), username)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scrumDataAccessService.getScrumsByUsername(username));
    }

    @GetMapping("{projectOwner}/{projectName}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public ResponseEntity<Object> getScrumsByProject(@PathVariable("projectOwner") String projectOwner, @PathVariable("projectName") String projectName, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService
                .getProjectByUsernameAndProjectName(projectOwner, projectName);

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("This project does not exist."));
        }

        if (project.get().getIsPublic()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(scrumDataAccessService.getScrumsByProject(projectOwner, projectName));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), projectOwner)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scrumDataAccessService.getScrumsByProject(projectOwner, projectName));
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public ResponseEntity<Object> addScrum(@RequestBody Scrum scrum, HttpEntity<byte[]> request) {
        Optional<Project> project = projectDataAccessService
                .getProjectByUsernameAndProjectName(scrum.getProjectOwner(), scrum.getProjectName());

        if(project.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("This project does not exist."));
        }

        if (project.get().getIsPublic()) {
            scrumDataAccessService.addScrum(scrum);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(scrum);
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), scrum.getProjectOwner())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        scrumDataAccessService.addScrum(scrum);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scrum);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteScrum(@PathVariable("id") Integer id, HttpEntity<byte[]> request) {
        Optional<Scrum> scrum = scrumDataAccessService.getScrumById(id);
        if(scrum.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Scrum with this id does not exist."));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), scrum.get().getCreatedBy())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        scrumDataAccessService.deleteScrum(id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(null);
    }

    @PatchMapping("{id}/{status}")
    @CrossOrigin(origins = "*", methods = RequestMethod.PATCH)
    public ResponseEntity<Object> changeScrumStatus(@PathVariable("id") Integer id, @PathVariable("status") String status, HttpEntity<byte[]> request) {
        Optional<Scrum> scrum = scrumDataAccessService.getScrumById(id);
        if(scrum.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("Scrum with this id does not exist."));
        }

        if (!authenticateUser.withToken(request.getHeaders().getFirst("token"), scrum.get().getCreatedBy())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiError("Invalid token."));
        }

        ScrumStatus statusE = ScrumStatus.TO_DO;
        if (status.equalsIgnoreCase("in_progress") || status.equalsIgnoreCase("in-progress")) statusE = ScrumStatus.IN_PROGRESS;
        else if (status.equalsIgnoreCase("done")) statusE = ScrumStatus.DONE;

        scrumDataAccessService.changeScrumStatus(id, statusE);

        Scrum res = scrum.get();
        res.setStatus(statusE);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(res);
    }

}
