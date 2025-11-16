package vet.vetclinic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationErrorResponse {
    private final String errorCode;
    private final String message;
    private final LocalDateTime timestamp;
    private final List<FieldError> errors;

    public ValidationErrorResponse(List<FieldError> errors) {
        this.errorCode = "VALIDATION_ERROR";
        this.message = "입력값 검증에 실패했습니다.";
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError{
        private String field;
        private String message;
    }
}
