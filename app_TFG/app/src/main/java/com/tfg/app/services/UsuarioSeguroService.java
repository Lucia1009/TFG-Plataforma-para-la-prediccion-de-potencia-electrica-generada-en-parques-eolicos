package com.tfg.app.services;

import com.tfg.app.dto.usuarios.UsuarioSeguroDto;
import com.tfg.app.dto.usuarios.UsuarioSeguroDtoPsw;
import com.tfg.app.modelo.UsuarioSeguro;
import com.tfg.app.repo.UsuarioSeguroRepo;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class UsuarioSeguroService {
    @Autowired
    private UsuarioSeguroRepo usuarioSeguroRepo;

    private ModelMapper modelMapper = new ModelMapper();

    public UsuarioSeguroService(UsuarioSeguroRepo usuarioSeguroRepo) {
        this.usuarioSeguroRepo = usuarioSeguroRepo;
    }

    public UsuarioSeguroDto saveUsuarioSeguro(UsuarioSeguroDtoPsw dtoPsw) {
        System.out.println("guardando"+dtoPsw+"..............");
        UsuarioSeguro entidad = modelMapper.map(dtoPsw, UsuarioSeguro.class);

        UsuarioSeguro guardado = usuarioSeguroRepo.save(entidad);


        return modelMapper.map(guardado, UsuarioSeguroDto.class);
    }

    public void eliminarPorEmail(String emailLogueado) {
        UsuarioSeguro u = usuarioSeguroRepo.findByEmail(emailLogueado);
        if (u != null) {
            usuarioSeguroRepo.delete(u);
        }
    }
}
