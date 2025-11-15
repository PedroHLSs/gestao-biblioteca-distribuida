package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {

    private final RestTemplate restTemplate;
    private final ServiceGestor serviceGestor;

    public UsuarioService(ServiceGestor serviceGestor) {
        this.serviceGestor = serviceGestor;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getUsuarios() {
        return serviceGestor.executarComFallback(url -> restTemplate.getForEntity(url + "/api/usuarios", String.class));
    }

    public ResponseEntity<String> getUsuarioPorId(Long id) {
        return serviceGestor
                .executarComFallback(url -> restTemplate.getForEntity(url + "/api/usuarios/" + id, String.class));
    }

    public ResponseEntity<String> salvarUsuario(RequestEntity<String> usuario) {
        return serviceGestor
                .executarComFallback(url -> restTemplate.postForEntity(url + "/api/usuarios", usuario, String.class));
    }

    public ResponseEntity<String> atualizarUsuario(Long id, RequestEntity<String> usuario) {
        return serviceGestor.executarComFallback(
                url -> restTemplate.exchange(url + "/api/usuarios/" + id, HttpMethod.PUT, usuario, String.class));
    }

    public void deletarUsuario(Long id) {
        serviceGestor.executarComFallback(url -> {
            restTemplate.delete(url + "/api/usuarios/" + id);
            return ResponseEntity.ok().build();
        });
    }
}
