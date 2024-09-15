package ru.skypro.homework.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
