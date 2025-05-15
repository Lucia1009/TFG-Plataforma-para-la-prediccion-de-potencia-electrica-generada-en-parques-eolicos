package com.tfg.app.dto.params;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "modelType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RFParamsDto.class, name = "rf"),
        @JsonSubTypes.Type(value = PRParamsDto.class, name = "pr"),
        @JsonSubTypes.Type(value = STParamsDto.class, name = "st")
})
public abstract class ParamsDto {
    private String modelType;
    private String target;
}
