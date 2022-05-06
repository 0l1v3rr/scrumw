package com.oliverr.scrumw.issue;

import java.util.List;
import java.util.Optional;

public interface IssueDao {
    List<Issue> getIssuesByUsername(String username);
    List<Issue> getLatestThreeIssues(String username);
    List<Issue> getIssuesByProject(String username, String projectName);
    void addIssue(Issue issue);
    void deleteIssue(int id);
    void closeIssue(int id, String closedBy);
    Optional<Issue> getIssueByIssueTitle(String username, String projectName, String issueTitle);
    Optional<Issue> getIssueById(int id);
    int getUsernameIssueCount(String username);
    long getUsernameClosedIssueCount(String username);
    long getUsernameOpenIssueCount(String username);
    int getProjectIssueCount(String username, String projectName);
    long getProjectClosedIssueCount(String username, String projectName);
    long getProjectOpenIssueCount(String username, String projectName);
}
