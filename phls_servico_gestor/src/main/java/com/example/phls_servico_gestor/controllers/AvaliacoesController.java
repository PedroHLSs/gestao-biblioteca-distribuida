package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.AvaliacaoService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/avaliacoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacoesController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacoesController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    public ResponseEntity<String> getAvaliacoes() {
        try {
            return avaliacaoService.getAvaliacao();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar avaliações: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAvaliacaoPorId(@PathVariable String id) {
        try {
            return avaliacaoService.getAvaliacaoPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar avaliação: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarAvaliacao(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/avaliacoes"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return avaliacaoService.salvarAvaliacao(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar avaliação: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAvaliacao(@PathVariable String id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/avaliacoes/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return avaliacaoService.atualizarAvaliacao(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar avaliação: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable String id) {
        try {
            avaliacaoService.deletarAvaliacao(id);
            return ResponseEntity.ok("Avaliação deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar avaliação: " + e.getMessage());
        }
    }
}
