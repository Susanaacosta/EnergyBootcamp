package com.energy.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de usuario para el sistema de gestión de energía")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(example = "admin@energy.com", description = "Email único que servirá como username")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contraseña encriptada")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(example = "ADMIN", description = "Roles: ADMIN, USER, ANALYST")
    private Role role;

    public enum Role {
        ADMIN, USER, ANALYST
    }
}