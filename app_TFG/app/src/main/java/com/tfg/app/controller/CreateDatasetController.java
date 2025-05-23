package com.tfg.app.controller;

import com.tfg.app.dto.ColumnasDto;
import com.tfg.app.dto.Dataset_ColumnasDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Controller
@Getter
@Setter
public class CreateDatasetController {

    @Value("${api.python.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    ColumnasDto columnasDto;

    @GetMapping("/createDataset")
    public String selectColumns(Model model) {
        columnasDto = restTemplate.getForObject(url+"get_columns", ColumnasDto.class);
        try {
            model.addAttribute("columnas", columnasDto.getColumns());
        }catch (NullPointerException n){
            model.addAttribute("columnas", Collections.emptyList());
            System.out.println(n.getMessage());
        }
        return "Create_dataset";
    }

    @PostMapping("/createDataset")
    public String createDataset(@RequestParam("target") String target, @RequestParam("direccion_viento") String wd,
                                @RequestParam("tiempo") String tiempo,@RequestParam("resto_columnas") List<String> restocolumnas) {
        Dataset_ColumnasDto dto = new Dataset_ColumnasDto(target, wd, tiempo, restocolumnas);

        restTemplate.postForObject(url+"createDataset", dto, Dataset_ColumnasDto.class);

        return "Seleccion_params_modelo";
    }
}
