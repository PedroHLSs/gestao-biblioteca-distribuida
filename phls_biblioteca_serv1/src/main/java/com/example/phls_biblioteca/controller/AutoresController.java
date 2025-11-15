package com.example.phls_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.phls_biblioteca.model.Autores;
import com.example.phls_biblioteca.service.AutoresService;

@RestController
@RequestMapping("/api/autores")
public class AutoresController {

    @Autowired
    private AutoresService autoresService;

     @GetMapping
    public List<Autores> buscarTodosAutores() {
        return autoresService.buscarTodosAutores();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Autores> getAutorPorId(@PathVariable Long id) {
        try {
            Autores autor = autoresService.buscarAutorPorId(id);
            return ResponseEntity.ok(autor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Autores criarAutores(@RequestBody Autores autores) {
        return autoresService.salvarAutores(autores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autores> atualizarAutores(@PathVariable Long id, @RequestBody Autores detalhesAutores) {
        try {
            Autores autoresAtualizados = autoresService.atualizarAutores(id, detalhesAutores);
            return ResponseEntity.ok(autoresAtualizados);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutores(@PathVariable Long id) {
        try {
            autoresService.deletarAutores(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
