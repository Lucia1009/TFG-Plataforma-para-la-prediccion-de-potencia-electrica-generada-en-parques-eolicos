package com.tfg.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColumnasDto {
    private String mensaje;
    private List<String> columns;

    public ColumnasDto(List<String> columnas) {
        this.columns = columnas;
    }
}
