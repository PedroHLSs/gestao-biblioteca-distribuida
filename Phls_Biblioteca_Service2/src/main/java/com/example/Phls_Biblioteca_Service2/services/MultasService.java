package com.example.Phls_Biblioteca_Service2.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Phls_Biblioteca_Service2.model.Emprestimo;
import com.example.Phls_Biblioteca_Service2.model.Multas;
import com.example.Phls_Biblioteca_Service2.repository.EmprestimoRepository;
import com.example.Phls_Biblioteca_Service2.repository.MultasRepository;

@Service
public class MultasService {

    private final MultasRepository multasRepository;
    private final EmprestimoRepository emprestimoRepository;

    public MultasService(MultasRepository multasRepository, EmprestimoRepository emprestimoRepository) {
        this.multasRepository = multasRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Multas> getTodasAsMultas() {
        return multasRepository.findAll();
    }

    public Multas buscarMultaPorId(Long idMulta) {
        return multasRepository.findById(idMulta)
                .orElseThrow(() -> new RuntimeException("Multa não encontrada com id: " + idMulta));
    }

    public Multas salvarMulta(Multas multa) {
        // Buscar o empréstimo existente no banco de dados
        if (multa.getEmprestimo() != null && multa.getEmprestimo().getIdEmprestimo() != null) {
            Emprestimo emprestimo = emprestimoRepository.findById(multa.getEmprestimo().getIdEmprestimo())
                    .orElseThrow(() -> new RuntimeException(
                            "Empréstimo não encontrado com id: " + multa.getEmprestimo().getIdEmprestimo()));
            multa.setEmprestimo(emprestimo);
        }
        return multasRepository.save(multa);
    }

    public void deletarMulta(Long idMulta) {
        multasRepository.deleteById(idMulta);
    }

    public Multas atualizarMulta(Long idMulta, Multas detalheMulta) {
        Multas multa = multasRepository.findById(idMulta)
                .orElseThrow(() -> new RuntimeException("Multa não encontrada com id: " + idMulta));

        // Buscar o empréstimo existente no banco de dados
        if (detalheMulta.getEmprestimo() != null && detalheMulta.getEmprestimo().getIdEmprestimo() != null) {
            Emprestimo emprestimo = emprestimoRepository.findById(detalheMulta.getEmprestimo().getIdEmprestimo())
                    .orElseThrow(() -> new RuntimeException(
                            "Empréstimo não encontrado com id: " + detalheMulta.getEmprestimo().getIdEmprestimo()));
            multa.setEmprestimo(emprestimo);
        }

        multa.setValorMulta(detalheMulta.getValorMulta());
        multa.setDescricao(detalheMulta.getDescricao());
        multa.setStatus(detalheMulta.getStatus());
        return multasRepository.save(multa);
    }
}
