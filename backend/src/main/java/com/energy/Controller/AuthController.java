package com.energy.Controller;

import com.energy.DTO.LoginRequest;
import com.energy.DTO.RegisterRequest;
import com.energy.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador encargado de manejar la autenticación de usuarios.
 * Permite registrar usuarios y realizar login para obtener un token JWT.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            String message = authService.register(request);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint para iniciar sesión.
     * Devuelve un token JWT si las credenciales son correctas.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}