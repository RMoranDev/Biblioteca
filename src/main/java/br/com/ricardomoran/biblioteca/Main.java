package br.com.ricardomoran.biblioteca;

import br.com.ricardomoran.biblioteca.model.Usuario;
import br.com.ricardomoran.biblioteca.repository.UsuarioRepository;

public class Main {

    public static void main(String[] args) {

        UsuarioRepository usuarioRepository = new UsuarioRepository();

        Usuario usuarioEncontrado = usuarioRepository.buscarPorId(1);

        //System.out.println(usuarioEncontrado.getNome());
        System.out.println("""
        ID: %d
        Nome: %s
        CPF: %s
        E-mail: %s
        Telefone: %s
        """.formatted(
                usuarioEncontrado.getId(),
                usuarioEncontrado.getNome(),
                usuarioEncontrado.getCpf(),
                usuarioEncontrado.getEmail(),
                usuarioEncontrado.getTelefone()
        ));

        System.out.println("Lista de Usuários:");
        for (Usuario u : usuarioRepository.buscarTodos()) {
            System.out.println(u.getId() + " - " + u.getNome());
        }

        Usuario usuarioParaAtualizar = usuarioRepository.buscarPorId(1L);

        if (usuarioParaAtualizar != null) {
            usuarioParaAtualizar.setNome("João Silva Atualizado");
            usuarioRepository.atualizar(usuarioParaAtualizar);
            System.out.println("Usuário atualizado com sucesso!");
        }
    }
}
