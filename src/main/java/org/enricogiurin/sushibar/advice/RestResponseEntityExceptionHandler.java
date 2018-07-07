package org.enricogiurin.sushibar.advice;


import org.enricogiurin.sushibar.util.StringResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<StringResponse> handleAccessDeniedException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                StringResponse.of("You don't have permission to access to this resource"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<StringResponse> handleBadCredentialsException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                StringResponse.of(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
