package com.carloseduardo.movie.search.data.model.exception;

public class UnknownModelException extends RuntimeException{

    public UnknownModelException() {
    }

    public UnknownModelException(String message) {
        super(message);
    }
}
