package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.UsuarioService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuariosController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<String> getUsuarios() {
        try {
            return usuarioService.getUsuarios();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUsuarioPorId(@PathVariable Long id) {
        try {
            return usuarioService.getUsuarioPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarUsuario(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/usuarios"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return usuarioService.salvarUsuario(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/usuarios/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return usuarioService.atualizarUsuario(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
