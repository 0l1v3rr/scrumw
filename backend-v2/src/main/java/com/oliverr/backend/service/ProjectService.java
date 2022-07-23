package com.oliverr.backend.service;

import com.oliverr.backend.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getProjectsByUsername(String username);
    List<Project> getPublicProjectsByUsername(String username);
    List<Project> getLatestProjectsByUsername(String username, Integer limit);
    Project saveProject(Project project);
    Project updateProject(Project project, int projectId);
    void deleteProject(int projectId);
    Project getProjectById(int projectId);
    Project getProjectByProjectName(String ownerName, String projectName);
    Integer getUserProjectCount(String username);
    Integer getUserPrivateProjectCount(String username);
    Project changeVisibility(int projectId);
}
