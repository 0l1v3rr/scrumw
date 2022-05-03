package com.oliverr.scrumw.project;

import java.util.List;
import java.util.Optional;

public interface ProjectDao {
    List<Project> getProjectsByUsername(String username);
    List<Project> getLatestThreeProject(String username);
    List<Project> getPublicProjectsByUsername(String username);
    List<Project> getPrivateProjectsByUsername(String username);
    void addProject(Project project);
    void updateProject(Project project, int id);
    void deleteProject(int id);
    Optional<Project> getProjectById(int id);
    Optional<Project> getProjectByUsernameAndProjectName(String username, String projectName);
    int getProjectCount(String username);
    int getPrivateProjectCount(String username);
    void changeVisibility(int id);
}
