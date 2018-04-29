package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 11.02.2017
 */
public class ReadInputTextException extends Exception {

    public ReadInputTextException() {
    }

    public ReadInputTextException(String message) {
        super(message);
    }

    public ReadInputTextException(String message, Throwable cause) {
        super(message, cause);
    }
}
