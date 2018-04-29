package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 11.02.2017
 */
public class SyntaxAnalysisException extends Exception {

    public SyntaxAnalysisException() {
    }

    public SyntaxAnalysisException(String message) {
        super(message);
    }

    public SyntaxAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
