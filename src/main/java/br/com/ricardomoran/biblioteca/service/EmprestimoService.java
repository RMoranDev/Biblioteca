package br.com.ricardomoran.biblioteca.service;

import br.com.ricardomoran.biblioteca.exception.ExemplarIndisponivelException;
import br.com.ricardomoran.biblioteca.model.Emprestimo;
import br.com.ricardomoran.biblioteca.model.StatusExemplar;
import br.com.ricardomoran.biblioteca.repository.EmprestimoRepository;
import br.com.ricardomoran.biblioteca.repository.ExemplarRepository;

import java.time.LocalDateTime;

public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final ExemplarRepository exemplarRepository;

    // Construtor "de produção" - cria as dependências reais
    public EmprestimoService() {
        this(new EmprestimoRepository(), new ExemplarRepository());
    }

    // Construtor "injetável" - recebe as dependências prontas
    public EmprestimoService(
            EmprestimoRepository emprestimoRepository,
            ExemplarRepository exemplarRepository
    ) {
        this.emprestimoRepository = emprestimoRepository;
        this.exemplarRepository = exemplarRepository;
    }

    public void realizarEmprestimo(Emprestimo emprestimo) {

        if (emprestimo.getExemplar().getStatus() == StatusExemplar.DISPONIVEL) {
            emprestimoRepository.salvar(emprestimo);
            emprestimo.getExemplar().setStatus(StatusExemplar.EMPRESTADO);
            exemplarRepository.atualizar(emprestimo.getExemplar());
        } else {
            throw new ExemplarIndisponivelException("O exemplar está indisponível.");
        }
    }

    public void realizarDevolucao(long idEmprestimo) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(idEmprestimo);

        if (emprestimo == null) {
            throw new RuntimeException("Empréstimo não encontrado.");
        }

        if (emprestimo.getDataDevolucao() != null) {
            throw new RuntimeException("Este empréstimo já foi devolvido.");
        }

        emprestimo.setDataDevolucao(LocalDateTime.now());
        emprestimoRepository.atualizar(emprestimo);
        emprestimo.getExemplar().setStatus(StatusExemplar.DISPONIVEL);
        exemplarRepository.atualizar(emprestimo.getExemplar());
    }
}
