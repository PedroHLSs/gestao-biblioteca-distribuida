package com.example.phls_servico_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.phls_servico_gestor.services.ReservaService;

import java.net.URI;

@RestController
@RequestMapping("/api/gestor/reservas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservasController {

    private final ReservaService reservaService;

    @Autowired
    public ReservasController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<String> getReservas() {
        try {
            return reservaService.getReserva();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar reservas: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getReservaPorId(@PathVariable Long id) {
        try {
            return reservaService.getReservaPorId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar reserva: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> salvarReserva(@RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .post(URI.create("/api/reservas"))
                    .header("Content-Type", "application/json")
                    .body(body);
            return reservaService.salvarReserva(requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar reserva: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarReserva(@PathVariable Long id, @RequestBody String body) {
        try {
            RequestEntity<String> requestEntity = RequestEntity
                    .put(URI.create("/api/reservas/" + id))
                    .header("Content-Type", "application/json")
                    .body(body);
            return reservaService.atualizarReserva(id, requestEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar reserva: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarReserva(@PathVariable Long id) {
        try {
            reservaService.deletarReserva(id);
            return ResponseEntity.ok("Reserva deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar reserva: " + e.getMessage());
        }
    }
}
