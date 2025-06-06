package com.tfg.app.modelo;

import java.io.Serializable;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {


    @Id
    private String rol;

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rol")
    private Set<UsuarioSeguro>  usuarios;
}

