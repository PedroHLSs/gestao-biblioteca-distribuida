package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MultaService {

    private final RestTemplate restTemplate;

    private String multasUrl = "http://localhost:8083";

    public MultaService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getMultas() {
        ResponseEntity<String> response = restTemplate.getForEntity(multasUrl + "/api/multas",
                String.class);

        return response;
    }

    public ResponseEntity<String> salvarMultas(RequestEntity<String> multa) {
        ResponseEntity<String> response = restTemplate.postForEntity(multasUrl + "/api/multas",
                multa,
                String.class);
        return response;
    }

    public ResponseEntity<String> getMultaPorId(Long id) {
        ResponseEntity<String> response = restTemplate.getForEntity(multasUrl + "/api/multas/" + id,
                String.class);
        return response;
    }

    public ResponseEntity<String> atualizarMultas(Long id, RequestEntity<String> multa) {
        ResponseEntity<String> response = restTemplate.exchange(
                multasUrl + "/api/multas/" + id, HttpMethod.PUT, multa, String.class);
        return response;
    }

    public void deletarMultas(Long id) {
        restTemplate.delete(multasUrl + "/api/multas/" + id);
    }
}
