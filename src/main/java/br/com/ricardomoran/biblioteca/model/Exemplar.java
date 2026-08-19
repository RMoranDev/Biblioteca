package br.com.ricardomoran.biblioteca.model;

import br.com.ricardomoran.biblioteca.util.ValidacaoUtil;

import java.util.Objects;

public class Exemplar {
    private long id;
    private String codigoPatrimonio;
    private StatusExemplar status = StatusExemplar.DISPONIVEL;
    private Livro livro;

    public Exemplar(String codigoPatrimonio, Livro livro) {
        this.codigoPatrimonio = ValidacaoUtil.validarObrigatorio(codigoPatrimonio, "Código de patrimônio");
        this.livro = Objects.requireNonNull(livro, "Livro é obrigatório");
    }


    public long getId() {
        return id;
    }

    public String getCodigoPatrimonio() {
        return codigoPatrimonio;
    }

    public void setCodigoPatrimonio(String codigoPatrimonio) {
        this.codigoPatrimonio = ValidacaoUtil.validarObrigatorio(codigoPatrimonio, "Código de patrimônio");
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
