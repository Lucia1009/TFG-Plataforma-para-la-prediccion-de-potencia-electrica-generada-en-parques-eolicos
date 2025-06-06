package com.tfg.app.config.service;

import com.tfg.app.modelo.UsuarioSeguro;

public interface IUsuarioSeguroService {
    public String getEncodedPassword(String passwd);
}
