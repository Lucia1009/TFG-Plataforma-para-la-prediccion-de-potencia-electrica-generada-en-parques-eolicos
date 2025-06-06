package com.tfg.app.dto.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RFParamsDto extends ParamsDto {
    private int numTrees;
    private int maxDepth;
    private String split_axis;
    private String categorical_algorithm;
    private String missing_value_policy;
    private String sparse_oblique_normalization;
    private boolean computeOobVariableImportances;
    private boolean winnerTakeAll;
    private boolean estratificado;
}
