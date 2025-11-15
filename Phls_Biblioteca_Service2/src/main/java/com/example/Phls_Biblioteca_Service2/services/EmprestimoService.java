package com.example.Phls_Biblioteca_Service2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Phls_Biblioteca_Service2.model.Emprestimo;
import com.example.Phls_Biblioteca_Service2.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> getTodosEmprestimos() {
        return emprestimoRepository.findAll();
    }
    public Emprestimo buscarEmprestimoPorId(Long idEmprestimo) {
        return emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Emprestimo não encontrado com id: " + idEmprestimo));
    }

    public Emprestimo salvarEmprestimo(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    public void deletarEmprestimo(Long idEmprestimo) {
        emprestimoRepository.deleteById(idEmprestimo);
    }

    public Emprestimo atualizarEmprestio(Long idEmprestimo, Emprestimo detalheEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.findById(idEmprestimo)
                .orElseThrow(() -> new RuntimeException("Emprestimo não encontrado com id: " + idEmprestimo));
        emprestimo.setIdUsuario(detalheEmprestimo.getIdUsuario());
        emprestimo.setIdLivro(detalheEmprestimo.getIdLivro());
        emprestimo.setDataEmprestimo(detalheEmprestimo.getDataEmprestimo());
        emprestimo.setDataPrevistaDevolucao(detalheEmprestimo.getDataPrevistaDevolucao());
        emprestimo.setDataDevolucao(detalheEmprestimo.getDataDevolucao());
        emprestimo.setStatus(detalheEmprestimo.getStatus());
        return emprestimoRepository.save(emprestimo);
    }
}
