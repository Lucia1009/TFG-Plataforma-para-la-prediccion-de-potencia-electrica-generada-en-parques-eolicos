package com.tfg.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "general")
@Getter
@Setter
public class Configuration_Properties {

    private String ippythonserver = "localhost" ;
}
