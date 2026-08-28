package br.com.ricardomoran.biblioteca;

import br.com.ricardomoran.biblioteca.model.*;
import br.com.ricardomoran.biblioteca.repository.ExemplarRepository;
import br.com.ricardomoran.biblioteca.repository.LivroRepository;
import br.com.ricardomoran.biblioteca.repository.UsuarioRepository;
import br.com.ricardomoran.biblioteca.service.EmprestimoService;

import java.time.Year;

public class Main {

    public static void main(String[] args) {

        LivroRepository livroRepository = new LivroRepository();
        ExemplarRepository exemplarRepository = new ExemplarRepository();
        Livro livro = livroRepository.buscarPorId(6L);

        Exemplar exemplar = new Exemplar("PAT-018", livro);
        Exemplar exemplar1 = new Exemplar("PAT-019", livro);
        Exemplar exemplar2 = new Exemplar("PAT-020", livro);
        Exemplar exemplar3 = new Exemplar("PAT-021", livro);
        exemplarRepository.salvar(exemplar);
        exemplarRepository.salvar(exemplar1);
        exemplarRepository.salvar(exemplar2);
        exemplarRepository.salvar(exemplar3);



    }
}

