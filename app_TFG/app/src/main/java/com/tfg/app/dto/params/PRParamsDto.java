package com.tfg.app.dto.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PRParamsDto extends ParamsDto {
    private int degree;
    private String optimizer;
    private String loss;
    private int epochs;
    private List<String> metrics;
    private List<CapaDto> layers;
}
