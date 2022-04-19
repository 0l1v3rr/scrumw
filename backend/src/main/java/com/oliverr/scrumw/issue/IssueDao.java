package com.oliverr.scrumw.issue;

import java.util.List;
import java.util.Optional;

public interface IssueDao {
    List<Issue> getIssuesByUsername(String username);
    List<Issue> getLatestThreeIssues(String username);
    List<Issue> getIssuesByProject(String username, String projectName);
    void addIssue(Issue issue);
    void deleteIssue(int id);
    Optional<Issue> getIssueByIssueTitle(String username, String projectName, String issueTitle);
    int getUsernameIssueCount(String username);
    int getUsernameClosedIssueCount(String username);
    int getUsernameOpenIssueCount(String username);
    int getProjectIssueCount(String username, String projectName);
    int getProjectClosedIssueCount(String username, String projectName);
    int getProjectOpenIssueCount(String username, String projectName);
}
