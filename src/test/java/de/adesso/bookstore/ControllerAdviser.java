package de.adesso.bookstore;

import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Class for controller adviser to map exceptions to error messages.
 */
@ControllerAdvice
public class ControllerAdviser {

    /**
     * Map ConstraintViolationException to a corresponding error message.
     *
     * @return ResponseEntity containing the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleException() {
        return ResponseEntity.badRequest().build();
    }
}
