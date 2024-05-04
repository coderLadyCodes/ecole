package com.samia.ecole;

import com.samia.ecole.entities.Role;
import com.samia.ecole.entities.User;
import com.samia.ecole.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@EnableMethodSecurity
@EnableWebSecurity
public class EcoleApplication implements CommandLineRunner {
    private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    public EcoleApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(EcoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User superAdmin = User.builder()
				.actif(true)
				.name("super admin")
				.password(passwordEncoder.encode("superAdmin"))
				.email("creativegirl@outlook.fr")
				.role(Role.SUPER_ADMIN)
				.build();
		superAdmin = this.userRepository.findByEmail("creativegirl@outlook.fr").orElse(superAdmin);
		this.userRepository.save(superAdmin);

		User admin = User.builder()
				.actif(true)
				.name("admin")
				.password(passwordEncoder.encode("admin"))
				.email("admin@outlook.fr")
				.role(Role.ADMIN)
				.build();
		admin = this.userRepository.findByEmail("admin@outlook.fr").orElse(admin);
		this.userRepository.save(admin);
	}
}
