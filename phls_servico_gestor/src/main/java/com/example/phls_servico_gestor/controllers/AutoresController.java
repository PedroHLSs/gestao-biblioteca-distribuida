package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.AutorService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/autores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AutoresController {

    private final AutorService autorService;

    @Autowired
    public AutoresController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<String> getAutores() {
        try {
            return autorService.getAutores();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar autores: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getAutorPorId(@PathVariable Long id) {
        try {
            return autorService.getAutorPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar autor: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarAutor(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/autores"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return autorService.salvarAutor(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar autor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAutor(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/autores/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return autorService.atualizarAutor(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar autor: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAutor(@PathVariable Long id) {
        try {
            autorService.deletarAutor(id);
            return ResponseEntity.ok("Autor deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar autor: " + e.getMessage());
        }
    }
}
