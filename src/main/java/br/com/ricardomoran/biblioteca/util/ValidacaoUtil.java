package br.com.ricardomoran.biblioteca.util;

public class ValidacaoUtil {
    public static String validarObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nomeCampo + " é obrigatório");
        }
        return valor;
    }
}
