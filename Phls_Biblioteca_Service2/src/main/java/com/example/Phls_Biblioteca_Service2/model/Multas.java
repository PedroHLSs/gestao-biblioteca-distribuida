package com.example.Phls_Biblioteca_Service2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "multas")
public class Multas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idMultas;

    @ManyToOne
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private Emprestimo emprestimo;

    private double valorMulta;
    private String descricao;
    private String status; // Pendente, pago, etc

    public Multas() {
    }

    public Multas(Long idMultas, Emprestimo emprestimo, double valorMulta, String descricao, String status) {
        this.idMultas = idMultas;
        this.emprestimo = emprestimo;
        this.valorMulta = valorMulta;
        this.descricao = descricao;
        this.status = status;
    }

    public Long getIdMultas() {
        return idMultas;
    }

    public void setIdMultas(Long idMultas) {
        this.idMultas = idMultas;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}