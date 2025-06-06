// src/main/java/com/tfg/app/config/SecurityConfig.java
package com.tfg.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // 1) Definimos el PasswordEncoder para toda la aplicación
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) Configuramos un DaoAuthenticationProvider que use nuestro UserDetailsService + el encriptador
    @Bean
    public DaoAuthenticationProvider authenticationProvider(BCryptPasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    // 3) Override de las convenciones de ROLE_ (opcional)
    @Bean
    public static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Si tus roles en BD vienen sin el prefijo "ROLE_", esto forzará a Spring a añadir "ROLE_" por defecto.
        return new GrantedAuthorityDefaults("ROLE_");
    }

    // 4) Configuración de la cadena de filtros
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider authProvider) throws Exception {
        http
                // Deshabilitamos CSRF para simplificar (en producción conviene mantenerlo activo)
                .csrf().disable()

                // Definimos qué rutas son públicas
                .authorizeHttpRequests(authorize -> authorize
                        // Recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/imgs/**").permitAll()
                        // Registro y página de registro
                        .requestMatchers("/usuarios/registro", "/usuarios/registro/**", "/error").permitAll()
                        // Páginas de login
                        .requestMatchers("/usuarios/login", "/login-error", "/usuarios/login?error", "/usuarios/logout?logout").permitAll()
                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                // Configuramos el login personalizado
                .formLogin(form -> form
                        // Página que muestra el formulario (debe corresponder a un @GetMapping("/usuarios/login"))
                        .loginPage("/usuarios/login")
                        // URL que procesará Spring automáticamente (POST a esta URL)
                        .loginProcessingUrl("/usuarios/login")
                        // Nombre de los campos del formulario
                        .usernameParameter("email")
                        .passwordParameter("password")
                        // A dónde ir tras login exitoso (redirect /index)
                        .defaultSuccessUrl("/index", true)
                        // A dónde ir tras fallo de login
                        .failureUrl("/usuarios/login?error")
                        .permitAll()
                )

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/usuarios/logout")
                        .logoutSuccessUrl("/usuarios/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                // Registramos nuestro AuthenticationProvider
                .authenticationProvider(authProvider)
        ;

        return http.build();
    }
}
