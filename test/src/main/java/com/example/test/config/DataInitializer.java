package com.example.test.config;
import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.repository.UserRepository;

import com.example.test.repository.roleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           roleRepository roleRepository,
                           PasswordEncoder encoder) {
        return args -> {
            // Créer les rôles s’ils n’existent pas
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role( "ROLE_ADMIN"));
            }
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role( "ROLE_USER"));
            }

            // Créer l’utilisateur admin s’il n’existe pas
            if (userRepository.findByUsername("admin@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin@gmail.com");
                admin.setNom("admin");
                admin.setPrenom("admin");
                admin.setPassword(encoder.encode("123456"));
                admin.getRoles().add(roleRepository.findByName("ROLE_ADMIN").get());
                userRepository.save(admin);
            }
        };
    }
}
