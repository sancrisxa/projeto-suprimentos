package br.com.tjro.supribackend.Exceptions;

public class FornecedorNotFoundException extends RuntimeException {
    public FornecedorNotFoundException(String message) { super(message); }
}
