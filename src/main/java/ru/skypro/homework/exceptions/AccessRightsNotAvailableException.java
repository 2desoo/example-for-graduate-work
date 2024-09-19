package ru.skypro.homework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessRightsNotAvailableException extends RuntimeException{

    public AccessRightsNotAvailableException(String message) {
        super(message);
    }
}
