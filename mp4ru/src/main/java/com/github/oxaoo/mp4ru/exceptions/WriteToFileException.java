package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 11.02.2017
 */
public class WriteToFileException extends Exception {

    public WriteToFileException() {
    }

    public WriteToFileException(String message) {
        super(message);
    }

    public WriteToFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
