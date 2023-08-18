package br.com.tjro.supribackend.Exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message);
    }
}
