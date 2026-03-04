package com.energy.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite el uso de @PreAuthorize en los Controladores
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encripta la contraseñas
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitado para APIs REST
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin estado
            .authorizeHttpRequests(auth -> auth
                
                // Ruta publica del swuagger
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()

                //Ruta publica del registro
                .requestMatchers("/api/auth/**").permitAll()

                // Rutas protegidas x rol
                // Solo el ADMIN puede gestionar usuarios
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                
                // Los tres roles pueden ver los datos de energía
                .requestMatchers("/api/energy/**").hasAnyRole("ADMIN", "USER", "ANALYST")

                // Una peticion diferente pide login
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // Habilita la ventanita de login en el navegador/swagger

        return http.build();
    }
}