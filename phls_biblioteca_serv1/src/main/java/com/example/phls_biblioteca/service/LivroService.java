package com.example.phls_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.phls_biblioteca.model.Autores;
import com.example.phls_biblioteca.model.Categoria;
import com.example.phls_biblioteca.model.Editora;
import com.example.phls_biblioteca.model.Livro;
import com.example.phls_biblioteca.repository.AutoresRepository;
import com.example.phls_biblioteca.repository.CategoriaRepository;
import com.example.phls_biblioteca.repository.EditoraRepository;
import com.example.phls_biblioteca.repository.LivroRepository;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    @Autowired
    private final AutoresRepository autoresRepository;
   
    @Autowired
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private final EditoraRepository editoraRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository, AutoresRepository autoresRepository,
            CategoriaRepository categoriaRepository, EditoraRepository editoraRepository) {
        this.livroRepository = livroRepository;
        this.autoresRepository = autoresRepository;
        this.categoriaRepository = categoriaRepository;
        this.editoraRepository = editoraRepository;
    }

    public List<Livro> buscarTodosLivros() {
        return livroRepository.findAll();
    }
    
    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));
    }

    public Livro salvarLivro(Livro livro) {
        if (livro.getAutor() != null && livro.getAutor().getId() != null) {
            Autores autor = autoresRepository.findById(livro.getAutor().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Autor com ID " + livro.getAutor().getId() + " não encontrado"));
            livro.setAutor(autor);
        }
        if (livro.getCategoria() != null && livro.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(livro.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Categoria com ID " + livro.getCategoria().getId() + " não encontrada"));
            livro.setCategoria(categoria);
        }
        if (livro.getEditora() != null && livro.getEditora().getId() != null) {
            Editora editora = editoraRepository.findById(livro.getEditora().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Editora com ID " + livro.getEditora().getId() + " não encontrada"));
            livro.setEditora(editora);
        }
        return livroRepository.save(livro);
    }

    public Livro atualizarLivro(Long id, Livro detalhesLivro) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));

        livro.setTitulo(detalhesLivro.getTitulo());
        livro.setIsbn(detalhesLivro.getIsbn());
        livro.setAnoPublicacao(detalhesLivro.getAnoPublicacao());
        livro.setQuantidadeTotal(detalhesLivro.getQuantidadeTotal());
        livro.setQuantidadeDisponivel(detalhesLivro.getQuantidadeDisponivel());

        return livroRepository.save(livro);
    }

    public void deletarLivro(Long id) {
        livroRepository.deleteById(id);
    }
}
