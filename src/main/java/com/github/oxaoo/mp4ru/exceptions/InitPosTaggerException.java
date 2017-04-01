package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 01.04.2017
 */
public class InitPosTaggerException extends Exception {

    public InitPosTaggerException() {
    }

    public InitPosTaggerException(String message) {
        super(message);
    }

    public InitPosTaggerException(String message, Throwable cause) {
        super(message, cause);
    }
}
