package com.oliverr.backend.repository;

import com.oliverr.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM projects WHERE owner = ?1 ORDER BY created DESC;", nativeQuery = true)
    List<Project> findAllProjectsByUsername(String username);

    @Query(value = "SELECT * FROM projects WHERE owner = ?1 ORDER BY created DESC LIMIT ?2", nativeQuery = true)
    List<Project> findLatestProjectsByUsername(String username, Integer limit);
}
