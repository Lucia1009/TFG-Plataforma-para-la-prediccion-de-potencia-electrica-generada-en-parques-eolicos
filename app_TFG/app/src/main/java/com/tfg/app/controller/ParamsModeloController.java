package com.tfg.app.controller;

import com.tfg.app.dto.ColumnasDto;
import com.tfg.app.dto.params.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
            @RequestParam(value="compute_oob_variable_importances", defaultValue="false")
            boolean computeOob,
            @RequestParam(value="winner_take_all", defaultValue="false")
            boolean winnerTakeAll,

            /* ---------- Regresión polinomial ---------------- */
            @RequestParam(value="pr_metrics", required=false)       List<String> prMetrics,
            @RequestParam(value="pr_layerName", required=false)     List<String> prLayerNames,
            @RequestParam(value="pr_units", required=false)         List<String> prUnits,
            @RequestParam(value="pr_activation", required=false)    List<String> prActivations,

            /* ---------- Series Temporales ---------------- */
            @RequestParam(value="st_metrics", required=false)       List<String> stMetrics,
            @RequestParam(value="st_layerName", required=false)     List<String> stLayerNames,
            @RequestParam(value="st_units", required=false)         List<String> stUnits,
            @RequestParam(value="st_activation", required=false)    List<String> stActivations,

            /* ---------- Resto params normales ---------------- */
            @RequestParam Map<String, String> allParams
    ) {


        // 1) Construimos el DTO concreto
        ParamsDto dto;
        switch (modelType) {
            case "rf" -> {
                RFParamsDto rf = new RFParamsDto();
                rf.setModelType("rf");
                rf.setTarget(target);
                rf.setComputeOobVariableImportances(computeOob);
                rf.setWinnerTakeAll(winnerTakeAll);
                rf.setNumTrees(parseInt(allParams.get("num_trees")));
                rf.setMaxDepth(parseInt(allParams.get("max_depth")));
                rf.setSplit_axis(allParams.get("split_axis"));
                rf.setCategorical_algorithm(allParams.get("categorical_algorithm"));
                rf.setMissing_value_policy(allParams.get("missing_value_policy"));
                rf.setSparse_oblique_normalization(allParams.get("sparse_oblique_normalization"));
                dto = rf;
            }
            case "pr" -> {
                PRParamsDto pr = new PRParamsDto();
                pr.setModelType("pr");
                pr.setTarget(target);
                pr.setDegree(parseInt(allParams.get("degree")));
                pr.setOptimizer(allParams.get("optimizer"));
                pr.setLoss(allParams.get("loss"));
                pr.setEpochs(parseInt(allParams.get("epochs")));
                pr.setMetrics(prMetrics != null ? prMetrics : Collections.emptyList());
                pr.setLayers(buildLayers(prLayerNames, prUnits, prActivations));
                dto = pr;
            }
            case "st" -> {
                STParamsDto st = new STParamsDto();
                st.setModelType("st");
                st.setTarget(target);
                st.setDatosPasados(parseInt(allParams.get("datosPasados")));
                st.setUnidades_tiempo_pasadas(allParams.get("unidades_tiempo_pasadas"));
                st.setDatosFuturos(parseInt(allParams.get("datosFuturos")));
                st.setUnidades_tiempo_futuras(allParams.get("unidades_tiempo_futuras"));
                st.setEpochs(parseInt(allParams.get("epochs")));
                st.setBatchSize(parseInt(allParams.get("batchSize")));
                st.setOptimizer(allParams.get("optimizer"));
                st.setLoss(allParams.get("loss"));
                st.setMetrics(stMetrics != null ? stMetrics : Collections.emptyList());
                st.setLayers(buildLayers(stLayerNames, stUnits, stActivations));
                dto = st;
            }
            default -> throw new IllegalArgumentException("Unknown modelType: " + modelType);
        }

        // 2) Enviamos el DTO como JSON a /train_model
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ParamsDto> request = new HttpEntity<>(dto, headers);
        restTemplate.postForObject(url + "train_model", request, String.class);

        // 3) Redirigimos de nuevo al formulario
        return "train_complete";
    }

    private int parseInt(String s) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return 0; }
    }

    private List<CapaDto> buildLayers(List<String> names, List<String> units, List<String> activations) {
        if (names == null) return Collections.emptyList();
        List<CapaDto> capas = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            CapaDto c = new CapaDto();
            c.setName(names.get(i));
            c.setUnits(parseInt(units != null && units.size() > i ? units.get(i) : null));
            c.setActivation(activations != null && activations.size() > i
                    ? activations.get(i)
                    : null);
            capas.add(c);
        }
        return capas;
    }


}
