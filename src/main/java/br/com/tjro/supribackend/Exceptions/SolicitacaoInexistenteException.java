package br.com.tjro.supribackend.Exceptions;

public class SolicitacaoInexistenteException extends RuntimeException {
    public SolicitacaoInexistenteException(String message) {
        super(message);
    }
}
