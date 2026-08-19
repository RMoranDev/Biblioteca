package br.com.ricardomoran.biblioteca.model;

import br.com.ricardomoran.biblioteca.util.ValidacaoUtil;

import java.time.Year;
import java.util.Objects;

public class Livro {
    private long id;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private Year anoPublicacao;

    public Livro(String titulo, String autor, String editora, String isbn, Year anoPublicacao) {
        this.titulo = ValidacaoUtil.validarObrigatorio(titulo, "Título");
        this.autor = ValidacaoUtil.validarObrigatorio(autor, "Autor");
        this.editora = ValidacaoUtil.validarObrigatorio(editora, "Editora");
        this.anoPublicacao = Objects.requireNonNull(anoPublicacao, "Ano de publicação é obrigatório");
        this.isbn = isbn == null || isbn.isBlank() ? null : isbn; // opcional, pode ser null
    }


    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = ValidacaoUtil.validarObrigatorio(titulo, "Título");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = ValidacaoUtil.validarObrigatorio(autor, "Autor");
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = ValidacaoUtil.validarObrigatorio(editora, "Editora");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn == null || isbn.isBlank() ? null : isbn;
    }

    public Year getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Year anoPublicacao) {
        this.anoPublicacao = Objects.requireNonNull(anoPublicacao, "Ano de publicação é obrigatório");
    }
}
