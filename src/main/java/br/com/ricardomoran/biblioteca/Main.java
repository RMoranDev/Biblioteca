package br.com.ricardomoran.biblioteca;

import br.com.ricardomoran.biblioteca.model.*;
import br.com.ricardomoran.biblioteca.repository.EmprestimoRepository;
import br.com.ricardomoran.biblioteca.repository.ExemplarRepository;
import br.com.ricardomoran.biblioteca.repository.UsuarioRepository;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        ExemplarRepository exemplarRepository = new ExemplarRepository();
        EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

        Usuario usuario = usuarioRepository.buscarPorId(1);
        Exemplar exemplar = exemplarRepository.buscarPorId(1);

        if (usuario != null && exemplar != null) {

            Emprestimo emprestimo = new Emprestimo(
                    usuario,
                    exemplar,
                    LocalDateTime.now()
            );

            Emprestimo emprestimoSalvo =
                    emprestimoRepository.salvar(emprestimo);

            System.out.println(
                    "Empréstimo salvo com sucesso! ID: "
                            + emprestimoSalvo.getId()
            );
        } else {
            System.out.println(
                    "Usuário ou exemplar não encontrado."
            );
        }
    }
}

