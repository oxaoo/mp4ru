package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 18.03.2017
 */
public class FailedConllMapException extends Exception {

    public FailedConllMapException() {
    }

    public FailedConllMapException(String message) {
        super(message);
    }

    public FailedConllMapException(String message, Throwable cause) {
        super(message, cause);
    }
}
