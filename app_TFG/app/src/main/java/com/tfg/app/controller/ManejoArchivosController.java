package com.tfg.app.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Getter
@Setter
@Controller
public class ManejoArchivosController {

    @Value("${api.python.url}")
    private String url;

    private final RestTemplate restTemplate;

    public ManejoArchivosController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/upload")
    public String showUpload() {
        return "upload_file";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes flash) {
        boolean success;

        if (file.isEmpty()) {
            flash.addFlashAttribute("message", "Por favor, selecciona un archivo.");
            success = false;
        } else {
            try {
                // guardamos en temp y enviamos al microservicio Python
                Path tempFile = Files.createTempFile("upload-", "-" + file.getOriginalFilename());
                Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

                // llamamos al endpoint Python que devuelve ColumnasDto
                String respuesta = uploadToPython(tempFile.toFile());

                flash.addFlashAttribute("message", "Procesado con éxito.");
                success = true;

                tempFile.toFile().deleteOnExit();
            } catch (Exception e) {
                flash.addFlashAttribute("message", "Error: " + e.getMessage());
                success = false;
            }
        }

        // flash attribute para saber si redirigir tras aceptar
        flash.addFlashAttribute("success", success);

        // PRG: redirigimos a GET /upload para mostrar el modal
        return "redirect:/upload";
    }

    private String uploadToPython(File file) {
        // construye la petición multipart al microservicio Python
        var headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        var request = new org.springframework.http.HttpEntity<>(body, headers);
        return restTemplate.postForObject(url + "upload_f", request, String.class);
    }
}
