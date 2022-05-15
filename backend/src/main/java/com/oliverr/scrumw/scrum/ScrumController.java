package com.oliverr.scrumw.scrum;

import com.oliverr.scrumw.project.ProjectDataAccessService;
import com.oliverr.scrumw.user.UserDataAccessService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scrum")
@AllArgsConstructor
public class ScrumController {

    private UserDataAccessService userDataAccessService;
    private ScrumDataAccessService scrumDataAccessService;
    private ProjectDataAccessService projectDataAccessService;

    @GetMapping("{username}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public List<Scrum> getScrumsByUsername(@PathVariable("username") String username, HttpEntity<byte[]> requestEntity) {
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

        return scrumDataAccessService.getScrumsByUsername(username);
    }

    @GetMapping("{projectOwner}/{projectName}")
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    public List<Scrum> getScrumsByProject(@PathVariable("projectOwner") String projectOwner, @PathVariable("projectName") String projectName, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService
                .getProjectByUsernameAndProjectName(projectOwner, projectName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException()));

        if(project.getIsPublic()) {
            return scrumDataAccessService.getScrumsByProject(projectOwner, projectName);
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(projectOwner)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        return scrumDataAccessService.getScrumsByProject(projectOwner, projectOwner);
    }

    @PostMapping
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    public void addScrum(@RequestBody Scrum scrum, HttpEntity<byte[]> requestEntity) {
        var project = projectDataAccessService
                .getProjectByUsernameAndProjectName(scrum.getProjectOwner(), scrum.getProjectName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This project does not exist.", new RuntimeException()));

        if(project.getIsPublic()) {
            scrumDataAccessService.addScrum(scrum);
            return;
        }

        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        if(!userByToken.getUsername().equalsIgnoreCase(scrum.getProjectOwner())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        scrumDataAccessService.addScrum(scrum);
    }

    @DeleteMapping("{id}")
    @CrossOrigin(origins = "*", methods = RequestMethod.DELETE)
    public void deleteScrum(@PathVariable("id") String sid, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        int id = Integer.parseInt(sid);
        var scrum = scrumDataAccessService
                .getScrumById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scrum with this id does not exist."));

        if(!userByToken.getUsername().equalsIgnoreCase(scrum.getCreatedBy())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        scrumDataAccessService.deleteScrum(id);
    }

    @PatchMapping("{id}/{status}")
    @CrossOrigin(origins = "*", methods = RequestMethod.PATCH)
    public void changeScrumStatus(@PathVariable("id") String sid, @PathVariable("status") String status, HttpEntity<byte[]> requestEntity) {
        String token = requestEntity.getHeaders().getFirst("token");
        if(token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You did not provide any token.", new RuntimeException());
        }

        var userByToken = userDataAccessService
                .getUserByToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException()));

        int id = Integer.parseInt(sid);
        var scrum = scrumDataAccessService
                .getScrumById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scrum with this id does not exist."));

        if(!userByToken.getUsername().equalsIgnoreCase(scrum.getCreatedBy())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.", new RuntimeException());
        }

        ScrumStatus statusE = ScrumStatus.TO_DO;
        if(status.equalsIgnoreCase("in_progress")) statusE = ScrumStatus.IN_PROGRESS;
        else if(status.equalsIgnoreCase("done")) statusE = ScrumStatus.DONE;

        scrumDataAccessService.changeScrumStatus(id, statusE);
    }

}
