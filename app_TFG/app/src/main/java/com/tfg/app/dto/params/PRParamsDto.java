package com.tfg.app.dto.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PRParamsDto extends ParamsDto {
    private int degree;
    private String optimizer;
    private String loss;
    private int epochs;
    private List<String> metrics;
    private List<CapaDto> layers;
    private boolean estratificado;
}
