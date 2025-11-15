package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.EmprestimoService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/emprestimos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmprestimosController {

    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimosController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<String> getEmprestimos() {
        try {
            return emprestimoService.getEmprestimo();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar empréstimos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmprestimoPorId(@PathVariable Long id) {
        try {
            return emprestimoService.getEmprestimoPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar empréstimo: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarEmprestimo(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/emprestimos"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return emprestimoService.salvarEmprestimo(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar empréstimo: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEmprestimo(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/emprestimos/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return emprestimoService.atualizarEmprestimo(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEmprestimo(@PathVariable Long id) {
        try {
            emprestimoService.deletarEmprestimo(id);
            return ResponseEntity.ok("Empréstimo deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar empréstimo: " + e.getMessage());
        }
    }
}
