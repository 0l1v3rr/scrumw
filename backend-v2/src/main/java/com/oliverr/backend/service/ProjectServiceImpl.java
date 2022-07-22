package com.oliverr.backend.service;

import com.oliverr.backend.exception.BadRequestException;
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
    public Project updateProject(Project project, int projectId) {
        return null;
    }

    @Override
    public void deleteProject(int projectId) {

    }

    @Override
    public Project getProjectById(int projectId) {
        return null;
    }

    @Override
    public Project getProjectByProjectName(String ownerName, String projectName) {
        return null;
    }

    @Override
    public Integer getUserProjectCount(String username) {
        return null;
    }

    @Override
    public Integer getUserPrivateProjectCount(String username) {
        return null;
    }

    @Override
    public Project changeVisibility(int projectId) {
        return null;
    }

}
