package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.EditoraService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/editoras")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EditorasController {

    private final EditoraService editoraService;

    @Autowired
    public EditorasController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    @GetMapping
    public ResponseEntity<String> getEditoras() {
        try {
            return editoraService.getEditoras();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar editoras: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getEditoraPorId(@PathVariable Long id) {
        try {
            return editoraService.getEditoraPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar editora: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarEditora(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/editoras"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return editoraService.salvarEditora(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar editora: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEditora(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/editoras/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return editoraService.atualizarEditora(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar editora: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEditora(@PathVariable Long id) {
        try {
            editoraService.deletarEditora(id);
            return ResponseEntity.ok("Editora deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar editora: " + e.getMessage());
        }
    }
}
