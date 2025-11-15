package com.example.Phls_Biblioteca_Service2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Phls_Biblioteca_Service2.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
}
