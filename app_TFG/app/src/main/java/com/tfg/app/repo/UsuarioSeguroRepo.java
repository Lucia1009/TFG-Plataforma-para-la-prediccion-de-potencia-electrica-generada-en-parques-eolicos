package com.tfg.app.repo;

import com.tfg.app.dto.usuarios.UsuarioSeguroDtoPsw;
import com.tfg.app.modelo.UsuarioSeguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioSeguroRepo extends JpaRepository<UsuarioSeguro, String> {
    @Query("SELECT u FROM UsuarioSeguro u WHERE u.email = ?1")
    UsuarioSeguro findByEmail(String email);

    @Query("Select count(email) from UsuarioSeguro u where email= ?1 and password = ?2")
    Integer repValidarPassword(String username, String password);

    @Query("delete from UsuarioSeguro u where email=?1")
    void deleteByEmail(String email);


}
