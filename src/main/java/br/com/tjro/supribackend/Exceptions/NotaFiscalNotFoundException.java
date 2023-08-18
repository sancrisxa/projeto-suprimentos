package br.com.tjro.supribackend.Exceptions;

public class NotaFiscalNotFoundException extends RuntimeException {
    public NotaFiscalNotFoundException(String message) {
        super(message);
    }
}
