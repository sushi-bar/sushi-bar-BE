package org.enricogiurin.sushibar.advice;


import org.enricogiurin.sushibar.exception.SBException;
import org.enricogiurin.sushibar.util.StringResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by enrico on 9/7/17.
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<StringResponse> handleConflict(RuntimeException ex, WebRequest request) {
        if (ex instanceof SBException) {
            return new ResponseEntity<>(StringResponse.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        logger.error("unexpected exception", ex);
        return new ResponseEntity<>(StringResponse.of(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
