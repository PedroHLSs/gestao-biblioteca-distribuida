package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.MultaService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/multas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MultasController {

    private final MultaService multaService;

    @Autowired
    public MultasController(MultaService multaService) {
        this.multaService = multaService;
    }

    @GetMapping
    public ResponseEntity<String> getMultas() {
        try {
            return multaService.getMultas();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar multas: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMultaPorId(@PathVariable Long id) {
        try {
            return multaService.getMultaPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar multa: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarMulta(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/multas"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return multaService.salvarMultas(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar multa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMulta(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/multas/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return multaService.atualizarMultas(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar multa: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMulta(@PathVariable Long id) {
        try {
            multaService.deletarMultas(id);
            return ResponseEntity.ok("Multa deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar multa: " + e.getMessage());
        }
    }
}
