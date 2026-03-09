package com.energy.Config;

import com.energy.Model.UserModel;
import com.energy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@energy.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            UserModel admin = new UserModel();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("Admin"));
            admin.setRole("ADMIN");
            admin.setEnabled(true);

            userRepository.save(admin);

            System.out.println("Usuario admin creado correctamente: " + adminEmail);
        } else {
            System.out.println("El usuario admin ya existe.");
        }
    }
}