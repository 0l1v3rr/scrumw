package com.oliverr.scrumw.issue;

import java.util.List;
import java.util.Optional;

public class IssueDataAccessService implements IssueDao {

    @Override
    public List<Issue> getIssuesByUsername(String username) {
        return null;
    }

    @Override
    public List<Issue> getLatestThreeIssues(String username) {
        return null;
    }

    @Override
    public List<Issue> getIssuesByProject(String username, String projectName) {
        return null;
    }

    @Override
    public void addIssue(Issue issue) {

    }

    @Override
    public void deleteIssue(int id) {

    }

    @Override
    public Optional<Issue> getIssueByIssueTitle(String username, String projectName, String issueTitle) {
        return Optional.empty();
    }

    @Override
    public int getUsernameIssueCount(String username) {
        return 0;
    }

    @Override
    public int getUsernameClosedIssueCount(String username) {
        return 0;
    }

    @Override
    public int getUsernameOpenIssueCount(String username) {
        return 0;
    }

    @Override
    public int getProjectIssueCount(String username, String projectName) {
        return 0;
    }

    @Override
    public int getProjectClosedIssueCount(String username, String projectName) {
        return 0;
    }

    @Override
    public int getProjectOpenIssueCount(String username, String projectName) {
        return 0;
    }

}
