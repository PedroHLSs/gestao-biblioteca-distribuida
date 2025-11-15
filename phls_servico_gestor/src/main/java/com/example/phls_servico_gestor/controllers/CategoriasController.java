package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.CategoriaService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriasController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<String> getCategorias() {
        try {
            return categoriaService.getCategorias();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categorias: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCategoriaPorId(@PathVariable Long id) {
        try {
            return categoriaService.getCategoriaPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarCategoria(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/categorias"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return categoriaService.salvarCategoria(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar categoria: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarCategoria(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/categorias/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return categoriaService.atualizarCategoria(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCategoria(@PathVariable Long id) {
        try {
            categoriaService.deletarCategoria(id);
            return ResponseEntity.ok("Categoria deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar categoria: " + e.getMessage());
        }
    }
}
