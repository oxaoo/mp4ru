package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 11.02.2017
 */
public class FailedStoreTokensException extends Exception {

    public FailedStoreTokensException() {
    }

    public FailedStoreTokensException(String message) {
        super(message);
    }

    public FailedStoreTokensException(String message, Throwable cause) {
        super(message, cause);
    }
}
