package org.virtualsushibar.backend.app.exception;

public class SystemUnavailableException extends RuntimeException {
    public SystemUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemUnavailableException(Throwable cause) {
        super(cause);
    }
}
