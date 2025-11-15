package com.example.phls_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.phls_biblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    // Aqui podemos adicionar métodos personalizados de consulta se necessário
}