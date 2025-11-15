package com.example.phls_biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.phls_biblioteca.model.Autores;
import com.example.phls_biblioteca.repository.AutoresRepository;

@Service
public class AutoresService {

    private AutoresRepository autoresRepository;

     public AutoresService(AutoresRepository autoresRepository){
        this.autoresRepository = autoresRepository;
    }

    public List<Autores> buscarTodosAutores(){
        return autoresRepository.findAll();
    }

    public Autores buscarAutorPorId(Long id) {
        return autoresRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com o id: " + id));
    }

    public Autores salvarAutores(Autores autores){
        return autoresRepository.save(autores);
    }
    public void deletarAutores(Long id){
        autoresRepository.deleteById(id);
    }

    public Autores atualizarAutores(Long id, Autores detalhesAutores){
        Autores autores = autoresRepository.findById(id).orElseThrow(()-> new RuntimeException ("Autores não encontrada com o id: " + id));

        autores.setNome(detalhesAutores.getNome());
        autores.setNacionalidade(detalhesAutores.getNacionalidade());
        autores.setData_nascimento(detalhesAutores.getData_nascimento());

        return autoresRepository.save(autores);
    }
}
