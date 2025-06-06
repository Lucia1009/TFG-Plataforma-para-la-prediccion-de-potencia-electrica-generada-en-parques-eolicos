// src/main/java/com/tfg/app/controller/UsuarioSeguroController.java
package com.tfg.app.controller;

import com.tfg.app.config.service.UsuarioSeguroImpl;
import com.tfg.app.dto.usuarios.UsuarioSeguroDtoPsw;
import com.tfg.app.modelo.Role;
import com.tfg.app.modelo.UsuarioSeguro;
import com.tfg.app.services.RoleService;
import com.tfg.app.services.UsuarioSeguroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import static com.tfg.app.config.ValidarFormatoPassword.ValidarFormato;

@Controller
public class UsuarioSeguroController {

    private final UsuarioSeguroService usuarioService;
    private final RoleService roleService;
    private final UsuarioSeguroImpl usuarioSeguroImplService;

    public UsuarioSeguroController(
            UsuarioSeguroService usuarioService,
            RoleService roleService,
            UsuarioSeguroImpl usuarioSeguroImplService
    ) {
        this.usuarioService = usuarioService;
        this.roleService = roleService;
        this.usuarioSeguroImplService = usuarioSeguroImplService;
    }

    /* ==============================
       1) REGISTRO DE USUARIOS
       ============================== */

    @GetMapping("/usuarios/registro")
    public String vistaRegistro(Model model) {
        model.addAttribute("listaRoles", roleService.roleList());
        model.addAttribute("successMessage", "");
        model.addAttribute("errorMessage", "");
        model.addAttribute("datosUsuario", new UsuarioSeguroDtoPsw());
        return "registro";
    }

    @PostMapping("/usuarios/registro")
    public String guardarUsuario(
            @ModelAttribute("datosUsuario") UsuarioSeguroDtoPsw usuarioSeguroDtoPsw,
            @RequestParam("confirm_password") String contraseniaRepetida,
            Model model
    ) {
        // 1) Validar formato de contraseña
        if (!ValidarFormato(usuarioSeguroDtoPsw.getPassword())) {
            model.addAttribute("successMessage", "");
            model.addAttribute("errorMessage", "El formato de contraseña no es válido.");
            model.addAttribute("listaRoles", roleService.roleList());
            return "registro";
        }

        // 2) Validar que las contraseñas coincidan
        if (!contraseniaRepetida.equals(usuarioSeguroDtoPsw.getPassword())) {
            model.addAttribute("successMessage", "");
            model.addAttribute("errorMessage", "Las contraseñas no coinciden.");
            model.addAttribute("listaRoles", roleService.roleList());
            return "registro";
        }

        // 3) Mapeo a entidad
        UsuarioSeguro usuarioEntidad = usuarioService.getModelMapper().map(usuarioSeguroDtoPsw, UsuarioSeguro.class);

        // 4) Encriptar password
        String passEncriptada = usuarioSeguroImplService.getEncodedPassword(usuarioEntidad.getPassword());
        usuarioEntidad.setPassword(passEncriptada);

        // 5) Asignar rol
        Role rolEntidad = roleService.findByRol(usuarioSeguroDtoPsw.getRol());
        if (rolEntidad == null) {
            model.addAttribute("successMessage", "");
            model.addAttribute("errorMessage", "El rol seleccionado no existe.");
            model.addAttribute("listaRoles", roleService.roleList());
            return "registro";
        }
        usuarioEntidad.setRol(rolEntidad);

        // 6) Guardar en BD
        try {
            usuarioService.getUsuarioSeguroRepo().save(usuarioEntidad);
            model.addAttribute("successMessage", "El usuario se ha creado con éxito.");
            model.addAttribute("errorMessage", "");
            model.addAttribute("listaRoles", roleService.roleList());
            // Dejar el formulario limpio o redirigir a login si quieres:
            return "registro";
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("idx_usuario_email")) {
                model.addAttribute("successMessage", "");
                model.addAttribute("errorMessage", "El email ya está asociado a una cuenta. Intente con otro email.");
                model.addAttribute("listaRoles", roleService.roleList());
                return "registro";
            }
            throw ex; // Repitámoslo para no ocultar otros errores
        }
    }

    /* ==============================
       2) LOGIN DE USUARIOS
       ============================== */

    @GetMapping("/usuarios/login")
    public String verLogin(
            ModelMap modelMap,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "eliminado", required = false) String eliminado
    ) {
        if (error != null) {
            modelMap.addAttribute("errorMessage", "Usuario o contraseña incorrectos.");
        } else {
            modelMap.addAttribute("errorMessage", "");
        }

        if (logout != null) {
            modelMap.addAttribute("successMessage", "Has cerrado sesión correctamente.");
        } else if (eliminado != null) {
            modelMap.addAttribute("successMessage", "Tu cuenta ha sido eliminada.");
        } else {
            modelMap.addAttribute("successMessage", "");
        }

        modelMap.addAttribute("datosUsuario", new UsuarioSeguroDtoPsw());
        return "login";
    }


    @GetMapping("/usuarios/eliminar")
    public String eliminarCuenta(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {

        String emailLogueado = authentication.getName();
        usuarioService.eliminarPorEmail(emailLogueado);

        new SecurityContextLogoutHandler().logout(request, response, authentication);


        return "redirect:/usuarios/login?eliminado=true";
    }

}
