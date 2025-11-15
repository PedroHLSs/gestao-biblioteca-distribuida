package com.example.Phls_Biblioteca_Service2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Phls_Biblioteca_Service2.model.Reserva;
import com.example.Phls_Biblioteca_Service2.repository.ReservaRepository;

@Service
public class ReservaService {
    
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> getTodasAsReservas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarReservaPorId(Long idReserva) {
        return reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com id: " + idReserva));
    }
    public Reserva salvarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
    public void deletarReserva(Long idReserva) {
        reservaRepository.deleteById(idReserva);
    }
    public Reserva atualizarReserva(Long idReserva, Reserva detalheReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada com id: " + idReserva));
        reserva.setIdUsuario(detalheReserva.getIdUsuario());
        reserva.setIdLivro(detalheReserva.getIdLivro());
        reserva.setDataReserva(detalheReserva.getDataReserva());
        reserva.setStatus(detalheReserva.getStatus());
        return reservaRepository.save(reserva);
    }
}
