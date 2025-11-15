package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CategoriaService {

    private final RestTemplate restTemplate;
    private final ServiceGestor serviceGestor;

    public CategoriaService(ServiceGestor serviceGestor) {
        this.restTemplate = new RestTemplate();
        this.serviceGestor = serviceGestor;
    }
    
   public ResponseEntity<String> getCategorias() {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/categorias", String.class));
    }

    public ResponseEntity<String> getCategoriaPorId(Long id) {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/categorias/" + id, String.class));
    }

    public ResponseEntity<String> salvarCategoria(RequestEntity<String> categoria) {
        return serviceGestor.executarComFallback(url -> restTemplate.postForEntity(url + "/api/categorias", categoria, String.class));
    }

    public ResponseEntity<String> atualizarCategoria(Long id, RequestEntity<String> categoria) {
        return serviceGestor.executarComFallback(
                url -> restTemplate.exchange(url + "/api/categorias/" + id, HttpMethod.PUT, categoria, String.class));
    }

    public void deletarCategoria(Long id) {
        serviceGestor.executarComFallback(url -> {
            restTemplate.delete(url + "/api/categorias/" + id);
            return ResponseEntity.ok().build();
        });
    }
}
