package com.tfg.app.dto.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class STParamsDto extends ParamsDto {
    private int datosPasados;
    private String unidades_tiempo_pasadas;
    private int datosFuturos;
    private String unidades_tiempo_futuras;
    private int epochs;
    private int batchSize;
    private String optimizer;
    private String loss;
    private List<String> metrics;
    private List<CapaDto> layers;
}
