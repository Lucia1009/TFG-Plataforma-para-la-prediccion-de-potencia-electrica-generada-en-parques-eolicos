package com.tfg.app.dto.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CapaDto {
    private String name;
    private int units;
    private float rate;
    private String activation;
}
