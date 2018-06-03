package org.enricogiurin.sushibar.exception;

public class SBException extends RuntimeException {

    public SBException(String message) {
        super(message);
    }

    public SBException(String message, Throwable cause) {
        super(message, cause);
    }
}
