package com.energy.Controller;

import com.energy.Model.UserModel;
import com.energy.Service.UserService; //Service para encriptar la clave
import com.energy.DTO.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios y autenticación")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // REGISTRO 
    @PostMapping("/register")
    @Operation(summary = "Registrar un nuevo usuario")
    public ResponseEntity<UserModel> register(@RequestBody UserModel user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.crearUsuario(user));
    }

    //LOGIN
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // OBTENER TODO
    @GetMapping
    @Operation(summary = "Obtener lista de todos los usuarios")
    public List<UserModel> getAll() {
        return userService.findAll();
    }
    //ELIMINA USUARIO
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public String delete(@PathVariable Long id) {
        return "Usuario eliminado";
    }
}