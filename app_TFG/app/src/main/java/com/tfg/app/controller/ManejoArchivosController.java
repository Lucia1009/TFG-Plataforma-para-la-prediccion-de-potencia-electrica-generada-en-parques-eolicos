package com.tfg.app.controller;

import com.tfg.app.dto.RespuestaUploadDto;
import com.tfg.app.services.ManejoArchivosService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
@Controller
public class ManejoArchivosController {

    ManejoArchivosService manejoArchivosService;
    RespuestaUploadDto respuestaUploadDto;

    public ManejoArchivosController(ManejoArchivosService manejoArchivosService) {
        this.manejoArchivosService = manejoArchivosService;
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
            RespuestaUploadDto respuesta = manejoArchivosService.uploadFile(tempFile.toFile());

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
        manejoArchivosService.setTarget(target);
        return "Seleccion_params_modelo";
    }


}
