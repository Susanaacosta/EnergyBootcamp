package com.energy.Service;

import com.energy.DTO.LoginRequest;    // DTO con D mayúscula
import com.energy.DTO.RegisterRequest; // DTO con D mayúscula
import com.energy.Model.UserModel;
import com.energy.Repository.UserRepository;
import com.energy.Config.JwtUtil;      // Paquete donde tienes tu JwtUtil
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * Proceso de Registro: Encripta la clave y guarda el UserModel.
     */
    public String register(RegisterRequest request) {
        // Verificar si el usuario ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Crear nueva instancia de tu modelo
        UserModel newUser = new UserModel();
        newUser.setEmail(request.getEmail());
        
        // Encriptar contraseña usando BCrypt
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Configuración por defecto
        newUser.setRole("USER");
        newUser.setEnabled(true);

        //  Persistir en PostgreSQL
        userRepository.save(newUser);

        return "Usuario registrado con éxito: " + request.getEmail();
    }

    /**
     * Proceso de Login: Valida credenciales y genera el TOKEN JWT.
     */
    public String login(LoginRequest request) {
        // Buscar al usuario por email
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar que la contraseña coincida con el hash de la DB
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar el Token (Pasamos el objeto 'user' completo)
        // Como tu JwtUtil pide 'UserDetails', y UserModel lo implementa, esto funciona.
        return jwtUtil.generateToken(user);
    }
}