package com.tfg.app.services;

import com.tfg.app.config.Configuration_Properties;
import com.tfg.app.dto.RespuestaUploadDto;
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
    private final String url;


    @Autowired
    private RestTemplate restTemplate;

    private final Configuration_Properties config;

    public ManejoArchivosService(Configuration_Properties config) {
        this.config = config;
        url="http://pythonapi:5000/";
    }


    public RespuestaUploadDto uploadFile(File file) {
        // 1. Cabeceras para multipart/form-data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Body con el fichero bajo la clave "file"
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 3. Llamada POST al endpoint fijo /upload
        return restTemplate.postForObject(url+"upload_f", requestEntity, RespuestaUploadDto.class);
    }

    public String setTarget(String target) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("target", target);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        return restTemplate.postForObject(url + "set_target", request, String.class);
    }

}
