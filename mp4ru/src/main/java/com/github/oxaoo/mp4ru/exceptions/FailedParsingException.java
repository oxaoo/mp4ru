package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 11.02.2017
 */
public class FailedParsingException extends Exception {

    public FailedParsingException() {
    }

    public FailedParsingException(String message) {
        super(message);
    }

    public FailedParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
