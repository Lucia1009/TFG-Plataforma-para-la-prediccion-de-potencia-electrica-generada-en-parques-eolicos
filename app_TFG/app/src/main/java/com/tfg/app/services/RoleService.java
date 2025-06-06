package com.tfg.app.services;

import com.tfg.app.modelo.Role;
import com.tfg.app.repo.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {this.roleRepo = roleRepo;}

    public List<Role> roleList() {
        System.out.println("roles encontrados:"+roleRepo.findAll());
        return roleRepo.findAll();
    }

    public Role findByRol(Role rol) {
        return roleRepo.findById(rol.getRol()).get();
    }
}
