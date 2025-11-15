package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AutorService {

    private final RestTemplate restTemplate;
    private final ServiceGestor serviceGestor;

    public AutorService(ServiceGestor serviceGestor) {
        this.serviceGestor = serviceGestor;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getAutores() {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/autores", String.class));
    }

    public ResponseEntity<String> getAutorPorId(Long id) {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/autores/" + id, String.class));
    }

    public ResponseEntity<String> salvarAutor(RequestEntity<String> autor) {
        return serviceGestor.executarComFallback(url -> restTemplate.postForEntity(url + "/api/autores", autor, String.class));
    }

    public ResponseEntity<String> atualizarAutor(Long id, RequestEntity<String> autor) {
        return serviceGestor.executarComFallback(
                url -> restTemplate.exchange(url + "/api/autores/" + id, HttpMethod.PUT, autor, String.class));
    }

    public void deletarAutor(Long id) {
        serviceGestor.executarComFallback(url -> {
            restTemplate.delete(url + "/api/autores/" + id);
            return ResponseEntity.ok().build();
        });
    }
}
