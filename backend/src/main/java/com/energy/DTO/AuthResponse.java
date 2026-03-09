package com.energy.DTO;

import lombok.Builder;
import lombok.Data;
//Respuesta del login
@Data
@Builder
public class AuthResponse {
    private String token;
    private String email;
    private String role;
}