package com.oliverr.backend.service;

import com.oliverr.backend.exception.NotFoundException;
import com.oliverr.backend.model.Role;
import com.oliverr.backend.model.User;
import com.oliverr.backend.repository.RoleRepository;
import com.oliverr.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findRole(String roleName) {
        return roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role with this name doesn't exist."));
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with this username doesn't exist."));

        Role role = roleRepository
                .findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role with this name doesn't exist."));

        user.getRoles().add(role);
    }

}
