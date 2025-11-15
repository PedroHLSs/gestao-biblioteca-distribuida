package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LivroService {

    private final ServiceGestor serviceGestor;
    private final RestTemplate restTemplate;

    public LivroService(ServiceGestor serviceGestor) {
        this.serviceGestor = serviceGestor;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getLivros() {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/livros", String.class));
    }

    public ResponseEntity<String> salvarLivro(RequestEntity<String> livro) {
        return serviceGestor
                .executarComFallback(url -> restTemplate.postForEntity(url + "/api/livros", livro, String.class));
    }

    public ResponseEntity<String> atualizarLivro(Long id, RequestEntity<String> livro) {
        return serviceGestor.executarComFallback(
                url -> restTemplate.exchange(url + "/api/livros/" + id, HttpMethod.PUT, livro, String.class));
    }

    public void deletarLivro(Long id) {
        serviceGestor.executarComFallback(url -> {
            restTemplate.delete(url + "/api/livros/" + id);
            return ResponseEntity.ok().build();
        });
    }

    public ResponseEntity<String> getLivroPorId(Long id) {
        return serviceGestor.executarComFallback(
                url -> restTemplate.getForEntity(url + "/api/livros/" + id, String.class));
    }
}
