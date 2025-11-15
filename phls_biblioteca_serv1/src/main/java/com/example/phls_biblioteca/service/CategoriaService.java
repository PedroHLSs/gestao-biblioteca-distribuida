package com.example.phls_biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.phls_biblioteca.model.Categoria;
import com.example.phls_biblioteca.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> buscarTodasCategorias(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o id: " + id));
    }

    public Categoria salvCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }
    public void deletarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }

    public Categoria atualizarCategoria(Long id, Categoria detalhesCategoria){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new RuntimeException ("Categoria não encontrada com o id: " + id));

        categoria.setNome(detalhesCategoria.getNome());
        categoria.setDescricao(detalhesCategoria.getDescricao());

        return categoriaRepository.save(categoria);
    }
}