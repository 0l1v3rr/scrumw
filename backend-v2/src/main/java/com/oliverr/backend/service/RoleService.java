package com.oliverr.backend.service;

import com.oliverr.backend.model.Role;

public interface RoleService {
    Role saveRole(Role role);
    Role findRole(String roleName);
    void addRoleToUser(String username, String roleName);
}
