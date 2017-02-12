package com.github.oxaoo.mp4ru.exceptions;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 11.02.2017
 */
public class FailedSyntaxAnalysisException extends Exception {

    public FailedSyntaxAnalysisException() {
    }

    public FailedSyntaxAnalysisException(String message) {
        super(message);
    }

    public FailedSyntaxAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
