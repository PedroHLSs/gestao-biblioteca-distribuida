package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.LivroService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/livros")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LivrosController {

    private final LivroService livroService;

    @Autowired
    public LivrosController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<String> getLivros() {
        try {
            return livroService.getLivros();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar livros: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarLivro(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("api/livros"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return livroService.salvarLivro(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar livro: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getLivro(@PathVariable Long id) {
        return livroService.getLivroPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarLivro(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("api/livros/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return livroService.atualizarLivro(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable Long id) {
        try {
            livroService.deletarLivro(id);
            return ResponseEntity.ok("Livro deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar livro: " + e.getMessage());
        }
    }
}
