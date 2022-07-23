package com.oliverr.backend.service;

import com.oliverr.backend.exception.BadRequestException;
import com.oliverr.backend.exception.ForbiddenException;
import com.oliverr.backend.exception.NotFoundException;
import com.oliverr.backend.model.Project;
import com.oliverr.backend.repository.ProjectRepository;
import com.oliverr.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public List<Project> getProjectsByUsername(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with this username doesn't exist."));

        return projectRepository.findAllProjectsByUsername(username);
    }

    @Override
    public List<Project> getPublicProjectsByUsername(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with this username doesn't exist."));

        return projectRepository.findPublicProjectsByUsername(username);
    }

    @Override
    public List<Project> getLatestProjectsByUsername(String username, Integer limit) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with this username doesn't exist."));

        if(limit < 1) {
            throw new BadRequestException("The limit should be bigger than one.");
        }

        return projectRepository.findLatestProjectsByUsername(username, limit);
    }

    @Override
    public Project saveProject(Project project) {
        project.setCreated(LocalDateTime.now());
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project, Long projectId, String username) {
        Project old = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with this ID doesn't exist."));

        if(!old.getOwner().equalsIgnoreCase(username)) {
            throw new ForbiddenException();
        }

        project.setId(projectId);
        project.setOwner(username);

        if(project.getIsPublic() == null) {
            project.setIsPublic(old.getIsPublic());
        }

        if(project.getProjectName() == null) {
            project.setProjectName(old.getProjectName());
        }

        if(project.getProjectDescription() == null) {
            project.setProjectDescription(old.getProjectDescription());
        }

        if(project.getCreated() == null) {
            project.setCreated(old.getCreated());
        }

        return projectRepository.save(project);
    }

    @Override
    public Project changeVisibility(Long projectId, String username) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with this ID doesn't exist."));

        if(!project.getOwner().equalsIgnoreCase(username)) {
            throw new ForbiddenException();
        }

        projectRepository.changeProjectVisibility(projectId);
        project.setIsPublic(!project.getIsPublic());
        return project;
    }

    @Override
    public void deleteProject(Long projectId, String username) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with this ID doesn't exist."));

        if(!project.getOwner().equalsIgnoreCase(username)) {
            throw new ForbiddenException();
        }

        projectRepository.delete(project);
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with this ID does not exist."));
    }

    @Override
    public Project getProjectByProjectName(String ownerName, String projectName) {
        return projectRepository.findProjectByProjectName(ownerName, projectName)
                .orElseThrow(() -> new NotFoundException("Project with this name doesn't exist."));
    }

    @Override
    public Integer getUserProjectCount(String username) {
        return null;
    }

    @Override
    public Integer getUserPrivateProjectCount(String username) {
        return null;
    }

}
