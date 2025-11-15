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

import com.example.Phls_Biblioteca_Service2.model.Reserva;
import com.example.Phls_Biblioteca_Service2.services.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> buscarTodasAsReservas() {
        return reservaService.getTodasAsReservas();
    }

    @PostMapping
    public Reserva criarReserva(@RequestBody Reserva reserva) {
        return reservaService.salvarReserva(reserva);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarReservaPorId(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.buscarReservaPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id_reserva}")
    public ResponseEntity<Reserva> atualizarReserva(@PathVariable Long id_reserva,
            @RequestBody Reserva detalheReserva) {
        try {
            Reserva reservaAtualizada = reservaService.atualizarReserva(id_reserva, detalheReserva);
            return ResponseEntity.ok(reservaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id_reserva}")
    public ResponseEntity<Void> deletarReserva(@PathVariable Long id_reserva) {
        try {
            reservaService.deletarReserva(id_reserva);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
