package br.com.ricardomoran.biblioteca.model;

import br.com.ricardomoran.biblioteca.util.CpfUtil;
import br.com.ricardomoran.biblioteca.util.ValidacaoUtil;

public class Usuario {
    private long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public Usuario(String nome, String cpf, String email, String telefone) {
        this.nome = ValidacaoUtil.validarObrigatorio(nome, "Nome");
        this.cpf = ValidacaoUtil.validarObrigatorio(cpf, "CPF");
        if (!CpfUtil.isCpfValido(this.cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.email = ValidacaoUtil.validarObrigatorio(email, "E-mail");
        this.telefone = ValidacaoUtil.validarObrigatorio(telefone, "Telefone");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = ValidacaoUtil.validarObrigatorio(nome, "Nome");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = ValidacaoUtil.validarObrigatorio(cpf, "CPF");
        if (!CpfUtil.isCpfValido(this.cpf)) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = ValidacaoUtil.validarObrigatorio(email, "E-mail");
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = ValidacaoUtil.validarObrigatorio(telefone, "Telefone");
    }


}
