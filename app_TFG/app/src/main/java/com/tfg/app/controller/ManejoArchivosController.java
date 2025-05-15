package com.tfg.app.controller;

import com.tfg.app.config.Configuration_Properties;
import com.tfg.app.dto.RespuestaUploadDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
@Controller
public class ManejoArchivosController {

    private final String url;

    @Autowired
    private RestTemplate restTemplate;

    private final Configuration_Properties config;
    RespuestaUploadDto respuestaUploadDto;

    public ManejoArchivosController(Configuration_Properties config) {
        this.config = config;
        url="http://pythonapi:5000/";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Por favor, selecciona un archivo.");
            return "index";
        }

        try {
            // Guardar temp
            Path tempFile = Files.createTempFile("upload-", "-" + file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Procesar con el service
            RespuestaUploadDto respuesta = this.uploadFile(tempFile.toFile());

            // Informar de la carga completa
            model.addAttribute("message", "Procesado con éxito.");
            model.addAttribute("columnas", respuesta.getColumns());
            setRespuestaUploadDto(respuesta);

            tempFile.toFile().deleteOnExit();
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        return "upload_file";
    }

    @GetMapping("upload_file")
    public String irUpload(ModelMap model) {
        model.addAttribute("file", "");
        return "upload_file";
    }

    @GetMapping("/upload")
    public String mostrarFormularioUpload(Model model) {
        model.addAttribute("columnas", respuestaUploadDto.getColumns());
        return "upload_file";
    }

    @PostMapping("/setTarget")
    public String seleccionModelo(ModelMap model, @RequestParam("target") String target) {
        System.out.println(target);
        this.setTarget(target);
        return "Seleccion_params_modelo";
    }


    public RespuestaUploadDto uploadFile(File file) {
        // 1. Cabeceras para multipart/form-data
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Body con el fichero bajo la clave "file"
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 3. Llamada POST al endpoint fijo /upload
        return restTemplate.postForObject(url+"upload_f", requestEntity, RespuestaUploadDto.class);
    }

    public String setTarget(String target) {

        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("target", target);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        return restTemplate.postForObject(url + "set_target", request, String.class);
    }


}
