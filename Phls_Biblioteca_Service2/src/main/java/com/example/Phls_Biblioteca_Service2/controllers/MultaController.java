package com.example.Phls_Biblioteca_Service2.controllers;

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

import com.example.Phls_Biblioteca_Service2.model.Multas;
import com.example.Phls_Biblioteca_Service2.services.MultasService;

@RestController
@RequestMapping("api/multas")
public class MultaController {
    
    @Autowired
    private MultasService multasService;

    @GetMapping
    public List<Multas> buscarTodasAsMultas() {
        return multasService.getTodasAsMultas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multas> buscarMultaPorId(@PathVariable Long id) {
        try {
            Multas multa = multasService.buscarMultaPorId(id);
            return ResponseEntity.ok(multa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }  

    @PostMapping
    public Multas criarMultas(@RequestBody Multas multa) {
        return multasService.salvarMulta(multa);
    }

    @PutMapping("/{id_multa}")
    public ResponseEntity<Multas> atualizarMulta(@PathVariable Long id_multa, @RequestBody Multas detalheMulta) {
        try {
            Multas multaAtualizada = multasService.atualizarMulta(id_multa, detalheMulta);
            return ResponseEntity.ok(multaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id_multa}")
    public ResponseEntity<Void> deletarMulta(@PathVariable Long id_multa){
        try {
            multasService.deletarMulta(id_multa);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
