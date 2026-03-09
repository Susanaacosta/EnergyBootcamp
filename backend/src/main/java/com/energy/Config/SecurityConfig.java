package com.energy.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;

  public SecurityConfig(JwtFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }
//Para administracion de token que se puede y quien puede acceder a la informacion
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para que Postman funcione
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // Permitir registro y login sin token
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Permitir swagger sin token
                .anyRequest().authenticated() // Todo lo demás requiere token
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No guardar sesiones en el servidor
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}