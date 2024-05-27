package com.samia.ecole;

import com.samia.ecole.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${superAdmin.email}")
    private String superAdminEmail;
    @Value("${superAdmin.password}")
    private String superAdminPassword;
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
//		User superAdmin = User.builder()
//				.actif(true)
//				.name("super admin")
//				.password(passwordEncoder.encode(superAdminPassword))
//				.email(superAdminEmail)
//				.role(Role.SUPER_ADMIN)
//				.build();
//		superAdmin = this.userRepository.findByEmail(superAdminEmail).orElse(superAdmin);
//		this.userRepository.save(superAdmin);

//		User admin = User.builder()
//				.actif(true)
//				.name("admin")
//				.password(passwordEncoder.encode(adminPassword))
//				.email(adminEmail)
//				.role(Role.ADMIN)
//				.build();
//		admin = this.userRepository.findByEmail(adminEmail).orElse(admin);
//		this.userRepository.save(admin);
//
//		User parent = User.builder()
//				.actif(true)
//				.name("parent")
//				.password(passwordEncoder.encode("parent"))
//				.email("parent@outlook.fr")
//				.role(Role.PARENT)
//				.build();
//		parent = this.userRepository.findByEmail("parent@outlook.fr").orElse(parent);
//		this.userRepository.save(parent);
	}
}

