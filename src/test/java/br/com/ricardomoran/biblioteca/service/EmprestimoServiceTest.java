package br.com.ricardomoran.biblioteca.service;

import br.com.ricardomoran.biblioteca.exception.ExemplarIndisponivelException;
import br.com.ricardomoran.biblioteca.model.*;
import br.com.ricardomoran.biblioteca.repository.EmprestimoRepository;
import br.com.ricardomoran.biblioteca.repository.ExemplarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;
    @Mock
    private ExemplarRepository exemplarRepository;
    private EmprestimoService emprestimoService;

    @BeforeEach
    void setUp() {
        emprestimoService =
                new EmprestimoService(emprestimoRepository, exemplarRepository);
    }

    @Test
    void deveAlterarStatusDoExemplarParaEmprestadoQuandoExemplarDisponivel() {
        Livro livro = new Livro("Clean Code", "Robert C. Martin", "pepeEditora", "1234567890", Year.of(2021) );
        Exemplar exemplar = new Exemplar("PAT-018", livro);
        Usuario usuario = new Usuario("Pepe", "52998224725", "teste@gmail.com", "419901234567");

        Emprestimo emprestimo = new Emprestimo(usuario, exemplar);
        emprestimoService.realizarEmprestimo(emprestimo);
        assertEquals(StatusExemplar.EMPRESTADO, exemplar.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoExemplarNaoEstiverDisponivel() {
        Livro livro = new Livro("Clean Code", "Robert C. Martin", "pepeEditora", "1234567890", Year.of(2021));
        Exemplar exemplar = new Exemplar("PAT-018", livro);
        exemplar.setStatus(StatusExemplar.EMPRESTADO); // pré-condição: já emprestado
        Usuario usuario = new Usuario("Pepe", "52998224725", "teste@gmail.com", "419901234567");

        Emprestimo emprestimo = new Emprestimo(usuario, exemplar);

        assertThrows(ExemplarIndisponivelException.class, () -> {
            emprestimoService.realizarEmprestimo(emprestimo);
        });

        verify(emprestimoRepository, never()).salvar(emprestimo);

    }
}
