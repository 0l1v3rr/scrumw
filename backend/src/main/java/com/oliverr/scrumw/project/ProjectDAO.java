package com.oliverr.scrumw.project;

import java.util.List;
import java.util.Optional;

public interface ProjectDAO {
    List<Project> getProjectsByUsername(String username);
    List<Project> getLatestThreeProject(String username);
    List<Project> getPublicProjectsByUsername(String username);
    List<Project> getPrivateProjectsByUsername(String username);
    void addProject(Project project);
    void deleteProject(int id);
    Optional<Project> getProjectById(int id);
    Optional<Project> getProjectByUsernameAndProjectName(String username, String projectName);
    int getProjectCount(String username);
    int getPrivateProjectCount(String username);
}
