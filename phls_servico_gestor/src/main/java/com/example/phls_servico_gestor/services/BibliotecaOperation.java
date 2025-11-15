package com.example.phls_servico_gestor.services;

import org.springframework.http.ResponseEntity;
// Define uma interface funcional para operações de biblioteca
@FunctionalInterface
public interface BibliotecaOperation {
    ResponseEntity<String> execute(String url) throws Exception;
}
