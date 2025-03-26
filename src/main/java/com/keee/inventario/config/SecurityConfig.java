package com.keee.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    /**
     * Configuración de seguridad con el filtro de seguridad.
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/public/**").permitAll()  // Rutas públicas
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Rutas solo para ADMIN
                        .anyRequest().authenticated()  // Resto de rutas requieren autenticación
                )
                .httpBasic(withDefaults()); // Para la autenticación básica HTTP

        return http.build();
    }
}
