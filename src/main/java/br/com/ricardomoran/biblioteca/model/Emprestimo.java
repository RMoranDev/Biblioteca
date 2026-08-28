package br.com.ricardomoran.biblioteca.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Emprestimo {
    private long id;
    private Usuario usuario;
    private Exemplar exemplar;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataPrevistaDevolucao;
    private LocalDateTime dataDevolucao;

    public Emprestimo(Usuario usuario, Exemplar exemplar) {
        this.usuario = Objects.requireNonNull(usuario, "Usuário é obrigatório");
        this.exemplar = Objects.requireNonNull(exemplar, "Exemplar é obrigatório");
        this.dataEmprestimo = LocalDateTime.now();
        this.dataPrevistaDevolucao = dataEmprestimo.plusDays(7);
    }

    public Emprestimo(
            Usuario usuario,
            Exemplar exemplar,
            LocalDateTime dataEmprestimo,
            LocalDateTime dataPrevistaDevolucao,
            LocalDateTime dataDevolucao
    ) {
        this.usuario = Objects.requireNonNull(usuario, "Usuário é obrigatório");
        this.exemplar = Objects.requireNonNull(exemplar, "Exemplar é obrigatório");
        this.dataEmprestimo =
                Objects.requireNonNull(dataEmprestimo, "Data de empréstimo é obrigatória");
        this.dataPrevistaDevolucao =
                Objects.requireNonNull(
                        dataPrevistaDevolucao,
                        "Data prevista de devolução é obrigatória"
                );
        this.dataDevolucao = dataDevolucao;
    }

    public long getId() {
        return id;
    }

    public void setId(long aLong) {
        this.id = aLong;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDateTime getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
