package vet.vetclinic.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vet.vetclinic.dto.response.ErrorResponse;
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

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorResponse response = ErrorResponse.of(
                exception.getErrorCode().name(),
                exception.getErrorCode().getMessage());

        return ResponseEntity
                .status(exception.getErrorCode().getStatus())
                .body(response);
    }

}
