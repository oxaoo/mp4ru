package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class ClassifierModelNotFoundException extends Exception {

    public ClassifierModelNotFoundException() {
    }

    public ClassifierModelNotFoundException(String message) {
        super(message);
    }

    public ClassifierModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
