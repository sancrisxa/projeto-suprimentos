package br.com.tjro.supribackend.Exceptions;

public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException(String message) {
        super(message);
    }
}
