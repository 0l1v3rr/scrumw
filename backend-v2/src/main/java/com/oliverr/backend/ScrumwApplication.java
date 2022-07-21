package com.oliverr.backend;

import com.oliverr.backend.model.Role;
import com.oliverr.backend.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	CommandLineRunner run(RoleService roleService) {
		return args -> {
			roleService.saveRole(new Role(null, "ROLE_USER"));
		};
	}

}
