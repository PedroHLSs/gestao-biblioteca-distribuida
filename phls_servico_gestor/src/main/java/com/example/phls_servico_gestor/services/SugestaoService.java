package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SugestaoService {

    private final RestTemplate restTemplate;
    
    private String sugestaoUrl = "http://localhost:8084";

    public SugestaoService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getSugestoes() {
        return restTemplate.getForEntity(sugestaoUrl + "/api/sugestoes", String.class);
    }

    public ResponseEntity<String> getSugestaoById(Long id) {
        return restTemplate.getForEntity(sugestaoUrl + "/api/sugestoes/" + id, String.class);
    }

    public ResponseEntity<String> getSugestaoByStringId(String id) {
        return restTemplate.getForEntity(sugestaoUrl + "/api/sugestoes/" + id, String.class);
    }

    public ResponseEntity<String> salvarSugestao(RequestEntity<String> sugestao) {
        return restTemplate.postForEntity(sugestaoUrl + "/api/sugestoes", sugestao, String.class);
    }

    public ResponseEntity<String> atualizarSugestao(String id, RequestEntity<String> sugestao) {
        return restTemplate.exchange(sugestaoUrl + "/api/sugestoes/" + id, HttpMethod.PUT, sugestao, String.class);
    }

    public void deletarSugestao(String id) {
        restTemplate.delete(sugestaoUrl + "/api/sugestoes/" + id);
    }
}
