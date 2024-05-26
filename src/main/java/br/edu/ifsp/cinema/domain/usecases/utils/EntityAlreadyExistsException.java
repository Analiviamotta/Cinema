package br.edu.ifsp.cinema.domain.usecases.utils;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
