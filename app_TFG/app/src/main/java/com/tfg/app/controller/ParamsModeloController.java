package com.tfg.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParamsModeloController {

    @GetMapping("/Seleccion_params_modelo")
    public String Seleccion_params_modelo() {
        return "Seleccion_params_modelo";
    }
}
