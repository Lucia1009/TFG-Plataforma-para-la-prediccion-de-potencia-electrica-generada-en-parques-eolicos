package com.tfg.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColumnasDto {
    private String mensaje;
    private List<String> columns;
}
