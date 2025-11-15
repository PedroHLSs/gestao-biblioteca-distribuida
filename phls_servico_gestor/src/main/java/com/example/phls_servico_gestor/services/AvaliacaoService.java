package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AvaliacaoService {

    private final RestTemplate restTemplate;

    private String avaliacaoUrl = "http://localhost:8084";

    public AvaliacaoService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getAvaliacao() {
        ResponseEntity<String> response = restTemplate.getForEntity(avaliacaoUrl + "/api/avaliacoes",
                String.class);

        return response;
    }

    public ResponseEntity<String> getAvaliacaoPorId(String id) {
        ResponseEntity<String> response = restTemplate.getForEntity(avaliacaoUrl + "/api/avaliacoes/" + id,
                String.class);
        return response;
    }

    public ResponseEntity<String> salvarAvaliacao(RequestEntity<String> avaliacao) {
        ResponseEntity<String> response = restTemplate.postForEntity(avaliacaoUrl + "/api/avaliacoes", avaliacao,
                String.class);
        return response;
    }

    public ResponseEntity<String> atualizarAvaliacao(String id, RequestEntity<String> avaliacao) {
        ResponseEntity<String> response = restTemplate.exchange(
                avaliacaoUrl + "/api/avaliacoes/" + id, HttpMethod.PUT, avaliacao, String.class);
        return response;
    }

    public void deletarAvaliacao(String id) {
        restTemplate.delete(avaliacaoUrl + "/api/avaliacoes/" + id);
    }

}
