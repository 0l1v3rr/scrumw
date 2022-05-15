package com.oliverr.scrumw.scrum;

import java.util.List;
import java.util.Optional;

public interface ScrumDao {
    List<Scrum> getScrumsByUsername(String username);
    List<Scrum> getScrumsByProject(String username, String projectName);
    void addScrum(Scrum scrum);
    void deleteScrum(int id);
    void changeScrumStatus(int id, ScrumStatus status);
    Optional<Scrum> getScrumById(int id);
    void updateScrum(int id);
}
