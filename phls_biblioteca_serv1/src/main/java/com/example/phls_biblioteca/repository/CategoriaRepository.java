package com.example.phls_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phls_biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Aqui podemos adicionar métodos personalizados de consulta se necessário
}