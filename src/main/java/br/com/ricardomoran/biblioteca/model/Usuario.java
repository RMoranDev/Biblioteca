package br.com.ricardomoran.biblioteca.model;

public class Usuario {
    private long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public Usuario(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }
}
