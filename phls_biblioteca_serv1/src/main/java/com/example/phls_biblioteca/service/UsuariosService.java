package com.example.phls_biblioteca.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.phls_biblioteca.model.Usuarios;
import com.example.phls_biblioteca.repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Usuarios> buscarTodosUsuarios() {
        return usuariosRepository.findAll();
    }

    public Usuarios buscarUsuariosPorId(Long id) {
        return usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));
    }

    public Usuarios salvarUsuarios(Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    public void deletarUsuarios(Long id) {
        usuariosRepository.deleteById(id);
    }

    public Usuarios update(Long id, Usuarios usuarioAtualizado) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + id));
        {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setTelefone(usuarioAtualizado.getTelefone());
            usuario.setEndereco(usuarioAtualizado.getEndereco());
            usuario.setRoles(usuarioAtualizado.getRoles());
            return usuariosRepository.save(usuario);
        }
    }
}