package com.oliverr.backend.repository;

import com.oliverr.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM projects WHERE owner = ?1 ORDER BY created DESC;", nativeQuery = true)
    List<Project> findAllProjectsByUsername(String username);

    @Query(value = "SELECT * FROM projects WHERE owner = ?1 ORDER BY created DESC LIMIT ?2", nativeQuery = true)
    List<Project> findLatestProjectsByUsername(String username, Integer limit);

    @Query(value = "SELECT * FROM projects WHERE owner = ?1 AND is_public = 1 ORDER BY created DESC", nativeQuery = true)
    List<Project> findPublicProjectsByUsername(String username);

    @Modifying
    @Query(value = "UPDATE projects SET is_public = NOT is_public WHERE id = ?1", nativeQuery = true)
    void changeProjectVisibility(Long id);
}
