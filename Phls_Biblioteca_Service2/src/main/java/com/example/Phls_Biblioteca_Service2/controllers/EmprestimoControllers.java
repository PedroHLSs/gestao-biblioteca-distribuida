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

import com.example.Phls_Biblioteca_Service2.model.Emprestimo;
import com.example.Phls_Biblioteca_Service2.services.EmprestimoService;

@RestController
@RequestMapping("api/emprestimos")
public class EmprestimoControllers {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> buscarTodosEmprestimos() {
        return emprestimoService.getTodosEmprestimos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoPorId(@PathVariable Long id) {
        try {
            Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(id);
            return ResponseEntity.ok(emprestimo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Emprestimo criarEmprestimo(@RequestBody Emprestimo emprestimo) {
        return emprestimoService.salvarEmprestimo(emprestimo);
    }

    @PutMapping("/{id_emprestimo}")
    public ResponseEntity<Emprestimo> autualizaEmprestimo(@PathVariable Long id_emprestimo, @RequestBody Emprestimo detalheEmprestimo) {
        try {
            Emprestimo emprestimoAtualizado = emprestimoService.atualizarEmprestio(id_emprestimo, detalheEmprestimo);
            return ResponseEntity.ok(emprestimoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id_emprestimo}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id_emprestimo){
        try {
            emprestimoService.deletarEmprestimo(id_emprestimo);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
