package com.tfg.app.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuario")
public class UsuarioSeguro {
    @Id
    private String email;
    private String nombre;
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol")
    private Role rol;

}
