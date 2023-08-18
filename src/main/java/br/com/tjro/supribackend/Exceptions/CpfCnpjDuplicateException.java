package br.com.tjro.supribackend.Exceptions;

public class CpfCnpjDuplicateException extends RuntimeException {
    public CpfCnpjDuplicateException(String message) {
        super(message);
    }
}
