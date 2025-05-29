package com.tfg.app.controller;

import com.tfg.app.dto.ColumnasDto;
import com.tfg.app.dto.EvalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class TrainCompleteController {

    @Value("${api.python.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/train_complete")
    public String trainComplete(ModelMap modelMap) {

        EvalDto evaluaciones = restTemplate.getForObject(url+"get_eval", EvalDto.class);
        modelMap.addAttribute("message", evaluaciones.getMensaje());
        System.out.println("JAVA");
        System.out.println(evaluaciones.getEvaluacion());

        try {
            modelMap.addAttribute("evaluaciones", evaluaciones.getEvaluacion());
        }catch (NullPointerException n){
            modelMap.addAttribute("evaluaciones", Collections.emptyList());
            System.out.println(n.getMessage());
        }
        return "train_complete";
    }
}
