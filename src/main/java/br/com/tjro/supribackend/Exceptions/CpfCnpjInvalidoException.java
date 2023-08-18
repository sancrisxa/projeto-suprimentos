package br.com.tjro.supribackend.Exceptions;

public class CpfCnpjInvalidoException extends RuntimeException {
    public CpfCnpjInvalidoException(String message) {
        super(message);
    }
}
