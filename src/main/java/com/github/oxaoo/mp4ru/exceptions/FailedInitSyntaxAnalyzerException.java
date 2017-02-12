package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 11.02.2017
 */
public class FailedInitSyntaxAnalyzerException extends Exception {

    public FailedInitSyntaxAnalyzerException() {
    }

    public FailedInitSyntaxAnalyzerException(String message) {
        super(message);
    }

    public FailedInitSyntaxAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }
}
