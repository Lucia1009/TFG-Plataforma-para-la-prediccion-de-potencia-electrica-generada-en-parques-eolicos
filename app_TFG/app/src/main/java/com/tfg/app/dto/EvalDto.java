package com.tfg.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class EvalDto {
    private String mensaje;
    private HashMap<String, Float> evaluacion;

}
