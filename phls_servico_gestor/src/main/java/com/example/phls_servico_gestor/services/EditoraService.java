package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EditoraService {

    private final RestTemplate restTemplate;
    private final ServiceGestor serviceGestor;

    public EditoraService(ServiceGestor serviceGestor){
        this.restTemplate = new RestTemplate();
        this.serviceGestor = serviceGestor;
    }

    public ResponseEntity<String> getEditoras() {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/editoras", String.class));
    }
    
    public ResponseEntity<String> getEditoraPorId(Long id) {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/editoras/" + id, String.class));
    }

    public ResponseEntity<String> salvarEditora(RequestEntity<String> editora) {
        return serviceGestor.executarComFallback(url -> restTemplate.postForEntity(url + "/api/editoras", editora, String.class));
    }

    public ResponseEntity<String> atualizarEditora(Long id, RequestEntity<String> editora) {
        return serviceGestor.executarComFallback(
                url -> restTemplate.exchange(url + "/api/editoras/" + id, HttpMethod.PUT, editora, String.class));
    }

    public void deletarEditora(Long id) {
        serviceGestor.executarComFallback(url -> {
            restTemplate.delete(url + "/api/editoras/" + id);
            return ResponseEntity.ok().build();
        });
    }
}
