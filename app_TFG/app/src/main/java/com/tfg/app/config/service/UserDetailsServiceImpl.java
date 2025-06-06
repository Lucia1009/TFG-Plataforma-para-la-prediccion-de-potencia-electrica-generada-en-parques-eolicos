// src/main/java/com/tfg/app/config/service/UserDetailsServiceImpl.java
package com.tfg.app.config.service;

import com.tfg.app.config.SuperCustomerUserDetails;
import com.tfg.app.modelo.Role;
import com.tfg.app.modelo.UsuarioSeguro;
import com.tfg.app.repo.UsuarioSeguroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioSeguroRepo userRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioSeguroRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioSeguro usuario = userRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }

        SuperCustomerUserDetails userDetails = new SuperCustomerUserDetails();
        userDetails.setUsername(usuario.getEmail());
        userDetails.setPassword(usuario.getPassword());
        userDetails.setEnabled(true);  // podrás cambiar esto si tu entidad tiene un flag
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);

        // Si tu entidad UsuarioSeguro tiene un solo Role:
        Role rolEntidad = usuario.getRol();
        if (rolEntidad != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(rolEntidad.getRol());
            userDetails.setAuthorities(Collections.singletonList(authority));
        } else {
            userDetails.setAuthorities(Collections.emptyList());
        }

        return userDetails;
    }
}
