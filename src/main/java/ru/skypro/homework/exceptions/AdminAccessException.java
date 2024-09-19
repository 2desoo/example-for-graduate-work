package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AdminAccessException extends RuntimeException{
    public AdminAccessException(String message) {
        super(message);
    }
}
