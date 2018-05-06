package org.enricogiurin.sushibar.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by enrico on 9/7/17.
 */
@NoArgsConstructor
public class SBException extends RuntimeException {

    public SBException(String message) {
        super(message);
    }
}
