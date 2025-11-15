package com.example.phls_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phls_biblioteca.model.Autores;

public interface AutoresRepository extends JpaRepository<Autores, Long>{
    
}
