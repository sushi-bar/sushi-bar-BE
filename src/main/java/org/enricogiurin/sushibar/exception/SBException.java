package org.enricogiurin.sushibar.exception;

/**
 * Created by enrico on 9/7/17.
 */
public class SBException extends RuntimeException {
    public SBException() {
    }

    public SBException(String message) {
        super(message);
    }

    public SBException(String message, Throwable cause) {
        super(message, cause);
    }

    public SBException(Throwable cause) {
        super(cause);
    }

    public SBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
