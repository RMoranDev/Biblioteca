package br.com.ricardomoran.biblioteca.model;

import java.util.Objects;

public class Exemplar {
    private long id;
    private String codigoPatrimonio;
    private StatusExemplar status = StatusExemplar.DISPONIVEL;
    private Livro livro;

    public Exemplar(String codigoPatrimonio, Livro livro) {
        this.codigoPatrimonio = validarObrigatorio(codigoPatrimonio, "Código de patrimônio");
        this.livro = Objects.requireNonNull(livro, "Livro é obrigatório");
    }

    private static String validarObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nomeCampo + " é obrigatório");
        }
        return valor;
    }

    public long getId() {
        return id;
    }

    public String getCodigoPatrimonio() {
        return codigoPatrimonio;
    }

    public void setCodigoPatrimonio(String codigoPatrimonio) {
        this.codigoPatrimonio = validarObrigatorio(codigoPatrimonio, "Código de patrimônio");
    }

    public StatusExemplar getStatus() {
        return status;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = Objects.requireNonNull(livro, "Livro é obrigatório");
    }


}
