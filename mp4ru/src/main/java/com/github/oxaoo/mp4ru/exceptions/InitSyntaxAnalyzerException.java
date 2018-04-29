package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 11.02.2017
 */
public class InitSyntaxAnalyzerException extends Exception {

    public InitSyntaxAnalyzerException() {
    }

    public InitSyntaxAnalyzerException(String message) {
        super(message);
    }

    public InitSyntaxAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }
}
