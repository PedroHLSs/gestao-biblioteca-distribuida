package com.example.phls_biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.phls_biblioteca.model.Editora;
import com.example.phls_biblioteca.repository.EditoraRepository;

@Service
public class EditoraService {

    private EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository){
        this.editoraRepository = editoraRepository;
    }

    public List<Editora> buscarTodasEditoras() {
        return editoraRepository.findAll();
    }
    public Editora buscarEditoraPorId(Long id) {
        return editoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editora não encontrada com o id: " + id));
    }

    public Editora salvarEditora(Editora editora) {
        return editoraRepository.save(editora);
    }

    public void deletarEditora(Long id) {
        editoraRepository.deleteById(id);
    }

    public Editora atualizarEditora(Long id, Editora detalhesEditora) {
        Editora editora = editoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editora não encontrada com o id: " + id));

        editora.setNome(detalhesEditora.getNome());
        editora.setCidade(detalhesEditora.getCidade());
        editora.setEstado(detalhesEditora.getEstado());
        editora.setPais(detalhesEditora.getPais());


        return editoraRepository.save(editora);
    }
}
