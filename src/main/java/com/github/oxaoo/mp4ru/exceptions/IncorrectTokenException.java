package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 11.02.2017
 */
public class IncorrectTokenException extends Exception {

    public IncorrectTokenException() {
    }

    public IncorrectTokenException(String message) {
        super(message);
    }

    public IncorrectTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
