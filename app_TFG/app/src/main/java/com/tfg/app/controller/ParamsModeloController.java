package com.tfg.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class ParamsModeloController {

    @GetMapping("/Seleccion_params_modelo")
    public String showSelectionForm(Model model) {
        return "Seleccion_params_modelo";
    }

    @PostMapping("/model/train")
    public String trainModel(
            @RequestParam("modelType") String modelType,
            // booleanos para Random Forest
            @RequestParam(value="compute_oob_variable_importances", defaultValue="false") boolean computeOob,
            @RequestParam(value="winner_take_all",                    defaultValue="false") boolean winnerTakeAll,
            // lista de métricas para PR (puede venir null)
            @RequestParam(value="metrics", required=false)           List<String> metrics,
            // el resto de parámetros genéricos en crudo
            @RequestParam Map<String, String> allParams,
            Model model
    ) {
        // Parámetros “simples” por modelo (sin metrics ni booleanos)
        Map<String, List<String>> expectedParams = Map.of(
                "rf", List.of("num_trees","max_depth","split_axis",
                        "categorical_algorithm","missing_value_policy","sparse_oblique_normalization"),
                "pr", List.of("degree","optimizer","loss","epochs"),
                "ts", List.of("lookBack","lstmUnits","epochs","batchSize")
        );

        System.out.println("=== Parámetros de " + modelType + " ===");
        // 1) Imprimimos los parámetros comunes
        for (String key : expectedParams.getOrDefault(modelType, Collections.emptyList())) {
            System.out.printf("%s = %s%n", key, allParams.get(key));
        }

        // 2) Métricas para PR
        if ("pr".equals(modelType)) {
            PrMetrics(metrics);
        }

        // 3) Booleanos para RF
        if ("rf".equals(modelType)) {
            RfBooleans(computeOob, winnerTakeAll);
        }

        model.addAttribute("message",
                "Parámetros de " + modelType + " recibidos y mostrados en consola."
        );
        return "Seleccion_params_modelo";
    }

    private void PrMetrics(List<String> metrics) {
        if (metrics != null && !metrics.isEmpty()) {
            System.out.println("metrics = " + String.join(", ", metrics));
        } else {
            System.out.println("metrics = []");
        }
    }

    private void RfBooleans(boolean computeOob, boolean winnerTakeAll) {
        System.out.printf("compute_oob_variable_importances = %s%n", computeOob);
        System.out.printf("winner_take_all                 = %s%n", winnerTakeAll);
    }
}
