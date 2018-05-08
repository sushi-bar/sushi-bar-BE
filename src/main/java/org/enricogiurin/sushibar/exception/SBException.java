package org.enricogiurin.sushibar.exception;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class SBException extends RuntimeException {

    public SBException(String message) {
        super(message);
    }
}
