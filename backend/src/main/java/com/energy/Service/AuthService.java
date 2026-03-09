package com.energy.Service;

import com.energy.Config.JwtUtil;
import com.energy.DTO.AuthResponse;
import com.energy.DTO.LoginRequest;
import com.energy.DTO.RegisterRequest;
import com.energy.Model.UserModel;
import com.energy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        UserModel newUser = new UserModel();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole("USER");
        newUser.setEnabled(true);

        userRepository.save(newUser);

        return "Usuario registrado con éxito: " + request.getEmail();
    }

    public AuthResponse login(LoginRequest request) {
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}