package com.energy.Service;
import com.energy.Model.UserModel;
import com.energy.Repository.UserRepository;
import com.energy.DTO.LoginRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import com.energy.Exception.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel crearUsuario(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        // Buscamos por el email que llega en el request
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Contraseña incorrecta");
        }
        return "Login correcto para: " + user.getEmail() + " con rol: " + user.getRole();
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }
}