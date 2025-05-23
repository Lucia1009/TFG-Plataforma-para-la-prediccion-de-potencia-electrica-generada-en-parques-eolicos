package com.tfg.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Dataset_ColumnasDto extends ColumnasDto{
    private String target;
    private String wd;
    private String time;

    public Dataset_ColumnasDto(String target, String wd, String time, List<String> columnas) {
        super(columnas);
        this.target = target;
        this.wd = wd;
        this.time = time;
    }
}
