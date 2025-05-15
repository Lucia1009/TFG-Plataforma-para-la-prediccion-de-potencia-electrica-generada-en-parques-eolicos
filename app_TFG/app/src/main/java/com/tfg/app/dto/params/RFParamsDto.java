package com.tfg.app.dto.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RFParamsDto extends ParamsDto {
    private int numTrees;
    private int maxDepth;
    private String split_axis;
    private String categorical_algorithm;
    private String missing_value_policy;
    private String sparse_oblique_normalization;
    private boolean computeOobVariableImportances;
    private boolean winnerTakeAll;
}
