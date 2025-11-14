package vet.vetclinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalRecordUpdateRequest {
    @NotNull
    @PastOrPresent(message = "날짜는 오늘 이전 날짜여야 합니다.")
    private LocalDate recordDate;

    @NotBlank
    @Size(min = 1, max = 500, message = "주관적 판단은 1자 이상 500자 이하여야 합니다.")
    private String subjective;

    @NotBlank
    @Size(min = 1, max = 500, message = "주관적 판단은 1자 이상 500자 이하여야 합니다.")
    private String objective;

    @NotBlank
    @Size(min = 1, max = 200, message = "확정 판단은 1자 이상 200자 이하여야 합니다.")
    private String assessment;

    @NotBlank
    @Size(min = 1, max = 500, message = "진료 계획은 1자 이상 500자 이하여야 합니다.")
    private String plan;
}
