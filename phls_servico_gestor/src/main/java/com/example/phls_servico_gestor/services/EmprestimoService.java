package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmprestimoService {

    private final RestTemplate restTemplate;

    private String emprestimoUrl = "http://localhost:8083";

    public EmprestimoService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getEmprestimo() {
        ResponseEntity<String> response = restTemplate.getForEntity(emprestimoUrl + "/api/emprestimos",
                String.class);

        return response;
    }

    public ResponseEntity<String> getEmprestimoPorId(Long id) {
        ResponseEntity<String> response = restTemplate.getForEntity(emprestimoUrl + "/api/emprestimos/" + id,
                String.class);
        return response;
    }

    public ResponseEntity<String> salvarEmprestimo(RequestEntity<String> emprestimo) {
        ResponseEntity<String> response = restTemplate.postForEntity(emprestimoUrl + "/api/emprestimos",
                emprestimo,
                String.class);
        return response;
    }

    public ResponseEntity<String> atualizarEmprestimo(Long id, RequestEntity<String> emprestimo) {
        ResponseEntity<String> response = restTemplate.exchange(
                emprestimoUrl + "/api/emprestimos/" + id, HttpMethod.PUT, emprestimo, String.class);
        return response;
    }

    public void deletarEmprestimo(Long id) {
        restTemplate.delete(emprestimoUrl + "/api/emprestimos/" + id);
    }

}
