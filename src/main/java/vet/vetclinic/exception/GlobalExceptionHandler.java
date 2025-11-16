package vet.vetclinic.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vet.vetclinic.dto.response.ValidationErrorResponse;
import vet.vetclinic.dto.response.ValidationErrorResponse.FieldError;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<FieldError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(errors);
        return ResponseEntity.badRequest().body(response);
    }
}
