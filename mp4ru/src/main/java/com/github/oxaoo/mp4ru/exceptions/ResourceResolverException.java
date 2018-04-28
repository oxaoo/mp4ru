package com.github.oxaoo.mp4ru.exceptions;

import java.io.IOException;

/**
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 01.04.2017
 */
public class ResourceResolverException extends IOException {
    public ResourceResolverException() {
        super();
    }

    public ResourceResolverException(String message) {
        super(message);
    }

    public ResourceResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}
