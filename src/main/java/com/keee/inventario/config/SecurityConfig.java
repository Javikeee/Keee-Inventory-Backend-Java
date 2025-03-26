package com.keee.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     *
     * El securityFilterChain es una configuración que define cómo las solicitudes HTTP deben ser filtradas y gestionadas en términos de seguridad.
     * Es donde configuras la protección de endpoints, autenticación, autorización y otras políticas de seguridad.
     * En Spring Security, puedes crear un @Configuration que contenga el filtro de seguridad utilizando securityFilterChain,
     * lo que te permitirá aplicar reglas de acceso a los endpoints de tu aplicación.
     *
     * @param http
     * @return
     * @throws Exception
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll() // Rutas públicas
                .antMatchers("/api/admin/**").hasRole("ADMIN") // Rutas solo para ADMIN
                .anyRequest().authenticated() // Resto de rutas requieren autenticación
                .and()
                .formLogin()
                .loginPage("/login") // Página de login personalizada
                .permitAll()
                .and()
                .logout()
                .permitAll();

        return http.build();
    }
}
