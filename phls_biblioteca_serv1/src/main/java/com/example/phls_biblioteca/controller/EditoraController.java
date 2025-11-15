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

import com.example.phls_biblioteca.model.Editora;
import com.example.phls_biblioteca.service.EditoraService;

@RestController
@RequestMapping("/api/editoras")
public class EditoraController {
    
    @Autowired
    private EditoraService editoraService;

    @GetMapping
    public List<Editora> buscarTodasEditoras() {
        return editoraService.buscarTodasEditoras();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Editora> buscarEditoraPorId(@PathVariable Long id)
    {
        try {
            Editora editora = editoraService.buscarEditoraPorId(id);
            return ResponseEntity.ok(editora);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public Editora criarEditora(@RequestBody Editora editora) {
        return editoraService.salvarEditora(editora);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editora> atualizarEditora(@PathVariable Long id, @RequestBody Editora detalhesEditora) {
        try {
            Editora editoraAtualizada = editoraService.atualizarEditora(id, detalhesEditora);
            return ResponseEntity.ok(editoraAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEditora(@PathVariable Long id) {
        try {
            editoraService.deletarEditora(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
