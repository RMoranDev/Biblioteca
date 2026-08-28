package br.com.ricardomoran.biblioteca.exception;

public class ExemplarIndisponivelException extends RuntimeException {
    public ExemplarIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
