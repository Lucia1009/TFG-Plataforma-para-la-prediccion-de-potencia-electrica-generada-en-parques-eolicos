package com.tfg.app.config.service;

import com.tfg.app.exceptions.UsrNotFoundExcption;
import com.tfg.app.modelo.Role;
import com.tfg.app.modelo.UsuarioSeguro;
import com.tfg.app.repo.UsuarioSeguroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioSeguroImpl implements IUsuarioSeguroService{

    private UsuarioSeguroRepo usuarioSeguroRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioSeguroImpl(UsuarioSeguroRepo usuarioSeguroRepo) {
        this.usuarioSeguroRepo = usuarioSeguroRepo;
    }

    @Override
    public String getEncodedPassword(String passwd) {

        return this.passwordEncoder.encode(passwd);
    }


    public UserDetails loadUserByUsername (String username) throws UsrNotFoundExcption{
        UsuarioSeguro usuario=usuarioSeguroRepo.findByEmail(username);
        Set<GrantedAuthority> ga =new HashSet<>();
        Role rol_usuario= usuario.getRol();
        ga.add(new SimpleGrantedAuthority(rol_usuario.getRol()));
        return null;
    }


}
