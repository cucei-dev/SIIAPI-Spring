package dev.cucei.siiapi.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an entity conflicts with an existing one.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
