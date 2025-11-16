package vet.vetclinic.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    VET404(HttpStatus.NOT_FOUND, "존재하지 않는 수의사입니다."),
    PET404(HttpStatus.NOT_FOUND, "존재하지 않는 환자입니다."),
    REC404(HttpStatus.NOT_FOUND, "존재하지 않는 진료 기록입니다."),
    OPN404(HttpStatus.NOT_FOUND, "존재하지 않는 진료 소견서입니다.");

    private final HttpStatus status;
    private final String message;
}
