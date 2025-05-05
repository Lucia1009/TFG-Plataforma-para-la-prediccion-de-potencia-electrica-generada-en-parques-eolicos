package com.tfg.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Service
public class ManejoArchivosService {
    private final String url = "http://localhost:5000/";

    @Autowired
    private RestTemplate restTemplate;


    public String uploadFile(File file) {
        // 1. Cabeceras para multipart/form-data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Body con el fichero bajo la clave "file"
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);

        // 3. Llamada POST al endpoint fijo /upload
        return restTemplate.postForObject(url+"upload", requestEntity, String.class);
    }

}
