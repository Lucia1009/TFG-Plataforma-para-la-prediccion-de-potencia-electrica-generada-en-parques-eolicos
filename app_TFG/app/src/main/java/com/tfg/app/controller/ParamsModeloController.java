package com.tfg.app.controller;

import com.tfg.app.dto.ColumnasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class ParamsModeloController {

    @Value("${api.python.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    ColumnasDto columnasDto;

    @GetMapping("/Seleccion_params_modelo")
    public String showSelectionForm(Model model) {
        columnasDto = restTemplate.getForObject(url+"get_columns", ColumnasDto.class);
        try {
            model.addAttribute("columnas", columnasDto.getColumns());
        }catch (NullPointerException n){
            model.addAttribute("columnas", Collections.emptyList());
            System.out.println(n.getMessage());
        }
        return "Seleccion_params_modelo";
    }

    /*@PostMapping("/setTarget")
    public String seleccionModelo(Model model, @RequestParam("target") String target) {

        setTarget(target);

        model.addAttribute("target", target);

        return "Seleccion_params_modelo";
    }*/




    @PostMapping("/model/train")
    public String trainModel(
            Model model,
            @RequestParam("modelType") String modelType,

            /* ---------- Target ---------------- */
            @RequestParam("target") String target,

            /* ---------- Random Forest ---------------- */
            //booleanos
            @RequestParam(value="compute_oob_variable_importances", defaultValue="false")
            boolean computeOob,
            @RequestParam(value="winner_take_all", defaultValue="false")
            boolean winnerTakeAll,

            /* ---------- Regresión polinomial ---------------- */
            // lista de métricas (puede venir null)
            @RequestParam(value="pr_metrics", required=false) List<String> pr_metrics,

            // parámetros de capas para PR (pueden venir null o vacíos)
            @RequestParam(value="pr_layerName", required=false) List<String> pr_layerNames,
            @RequestParam(value="pr_units",     required=false) List<String> pr_units,
            @RequestParam(value="pr_activation",required=false) List<String> pr_activations,

            /* ---------- Series Temporales ---------------- */
            // parámetros de capas para ST
            @RequestParam(value="st_metrics", required=false) List<String> st_metrics,
            @RequestParam(value="st_layerName", required=false) List<String> st_layerNames,
            @RequestParam(value="st_units",     required=false) List<String> st_units,
            @RequestParam(value="st_activation",required=false) List<String> st_activations,

            /* ---------- Resto params normales ---------------- */
            // todos los demás params genéricos
            @RequestParam Map<String, String> allParams
    ) {

        this.setTarget(target);

        Map<String, List<String>> expectedParams = Map.of(
                "rf", List.of("num_trees","max_depth","split_axis",
                        "categorical_algorithm","missing_value_policy","sparse_oblique_normalization"),
                "pr", List.of("degree","optimizer","loss","epochs"),
                "st", List.of("datosPasados", "unidades_tiempo_pasadas", "datosFuturos",
                        "unidades_tiempo_futuras","epochs","batchSize", "optimizer",
                        "loss")
        );

        System.out.println("=== Parámetros de " + modelType + " ===");
        // 1) Imprimo los parámetros “simples” definidos en expectedParams
        expectedParams.getOrDefault(modelType, Collections.emptyList())
                .forEach(key ->
                        System.out.printf("%s = %s%n", key, allParams.get(key))
                );

        // 2) Booleanos RF
        if ("rf".equals(modelType)) {
            RfBooleans(computeOob, winnerTakeAll);
        }

        // 3) Métricas y Capas PR / ST
        if ("pr".equals(modelType)) {
            Metrics(pr_metrics);
            printLayers(pr_layerNames, pr_units, pr_activations);
        }
        if ("st".equals(modelType)) {
            Metrics(st_metrics);
            printLayers(st_layerNames, st_units, st_activations);
        }

        return "redirect:/Seleccion_params_modelo";
    }
    public String setTarget(String target) {

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("target", target);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        return restTemplate.postForObject(url + "set_target", request, String.class);
    }

    private void Metrics(List<String> metrics) {
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

    private void printLayers(List<String> names, List<String> units, List<String> activations) {
        if (names == null || names.isEmpty()) {
            System.out.println("Capas = []");
            return;
        }
        System.out.println("=== Capas ===");
        for (int i = 0; i < names.size(); i++) {
            String n = names.get(i);
            String u = (units != null && units.size() > i) ? units.get(i) : "n/a";
            String a = (activations != null && activations.size() > i) ? activations.get(i) : "n/a";
            System.out.printf("Capa %d: nombre=%s, unidades=%s, activación=%s%n", i+1, n, u, a);
        }
    }
}
