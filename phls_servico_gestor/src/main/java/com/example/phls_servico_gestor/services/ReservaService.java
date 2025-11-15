package com.example.phls_servico_gestor.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservaService {

    private final RestTemplate restTemplate;

    private String reservaUrl = "http://localhost:8083";

    public ReservaService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> getReserva() {
        ResponseEntity<String> response = restTemplate.getForEntity(reservaUrl + "/api/reservas",
                String.class);

        return response;
    }

    public ResponseEntity<String> salvarReserva(RequestEntity<String> reserva) {
        ResponseEntity<String> response = restTemplate.postForEntity(reservaUrl + "/api/reservas", reserva,
                String.class);
        return response;
    }

    public ResponseEntity<String> getReservaPorId(Long id) {
        ResponseEntity<String> response = restTemplate.getForEntity(reservaUrl + "/api/reservas/" + id,
                String.class);
        return response;
    }

    public ResponseEntity<String> atualizarReserva(Long id, RequestEntity<String> reserva) {
        ResponseEntity<String> response = restTemplate.exchange(
                reservaUrl + "/api/reservas/" + id, HttpMethod.PUT, reserva, String.class);
        return response;
    }

    public void deletarReserva(Long id) {
        restTemplate.delete(reservaUrl + "/api/reservas/" + id);
    }
}
