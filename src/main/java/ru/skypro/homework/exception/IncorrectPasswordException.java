package ru.skypro.homework.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
