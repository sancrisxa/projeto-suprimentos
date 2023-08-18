package br.com.tjro.supribackend.Exceptions;

public class ErroAoSalvarException extends RuntimeException{
    public ErroAoSalvarException(String message) {
        super(message);
    }
}
