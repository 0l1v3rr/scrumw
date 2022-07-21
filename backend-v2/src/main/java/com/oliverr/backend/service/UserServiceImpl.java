package com.oliverr.backend.service;

import com.oliverr.backend.exception.ConflictException;
import com.oliverr.backend.exception.NotFoundException;
import com.oliverr.backend.model.User;
import com.oliverr.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public User findUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with this id doesn't exist."));
    }

    @Override
    public User findUser(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with this username doesn't exist."));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if(foundUser.isPresent()) {
            throw new ConflictException("User with this username already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.findRole("ROLE_USER"));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with this username doesn't exist."));

        Collection<SimpleGrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

}
