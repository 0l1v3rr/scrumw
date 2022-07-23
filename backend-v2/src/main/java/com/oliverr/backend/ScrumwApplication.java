package com.oliverr.backend;

import com.oliverr.backend.model.Project;
import com.oliverr.backend.model.Role;
import com.oliverr.backend.model.User;
import com.oliverr.backend.service.ProjectService;
import com.oliverr.backend.service.RoleService;
import com.oliverr.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class ScrumwApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumwApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner init(RoleService roleService, UserService userService, ProjectService projectService) {
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_USER"));

			userService.saveUser(new User(
					null,
					"john",
					"john@gmail.com",
					"test",
					null,
					null,
					new ArrayList<>()
			));

			projectService.saveProject(new Project(
					null,
					"john",
					"project-one",
					"This is the very first project.",
					true,
					LocalDateTime.now()
			));

			projectService.saveProject(new Project(
					null,
					"john",
					"project-two",
					"This is John's second project.",
					false,
					LocalDateTime.now()
			));
		};
	}

}
