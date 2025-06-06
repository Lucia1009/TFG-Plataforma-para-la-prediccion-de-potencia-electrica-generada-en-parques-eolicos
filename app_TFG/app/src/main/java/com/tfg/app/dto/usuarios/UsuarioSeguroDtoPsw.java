package com.tfg.app.dto.usuarios;

import com.tfg.app.modelo.Role;
import com.tfg.app.modelo.UsuarioSeguro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// No tiene contraseña para manejar e usuario de ofrma segura
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSeguroDtoPsw {

    private String email;
    private String nombre;
    private String password;
    private Role rol;


}
