package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.SugestaoService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/sugestoes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SugestoesController {

    private final SugestaoService sugestaoService;

    @Autowired
    public SugestoesController(SugestaoService sugestaoService) {
        this.sugestaoService = sugestaoService;
    }

    @GetMapping
    public ResponseEntity<String> getSugestoes() {
        try {
            return sugestaoService.getSugestoes();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar sugestões: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getSugestaoPorId(@PathVariable String id) {
        try {
            return sugestaoService.getSugestaoById(Long.parseLong(id));
        } catch (NumberFormatException e) {
            // Se não for número, tenta como String (MongoDB ObjectId)
            return sugestaoService.getSugestaoByStringId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar sugestão: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarSugestao(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/sugestoes"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return sugestaoService.salvarSugestao(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar sugestão: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarSugestao(@PathVariable String id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/sugestoes/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return sugestaoService.atualizarSugestao(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar sugestão: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSugestao(@PathVariable String id) {
        try {
            sugestaoService.deletarSugestao(id);
            return ResponseEntity.ok("Sugestão deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar sugestão: " + e.getMessage());
        }
    }
}
