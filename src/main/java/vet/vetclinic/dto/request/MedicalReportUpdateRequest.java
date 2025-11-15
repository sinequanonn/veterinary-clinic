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
public class MedicalReportUpdateRequest {
    @NotNull
    @PastOrPresent(message = "날짜는 오늘 이전 날짜여야 합니다.")
    private LocalDate reportDate;

    @NotBlank
    @Size(min = 1, max = 300, message = "주요 증상은 1자 이상 300자 이하여야 합니다.")
    private String chiefComplaint;

    @NotBlank
    @Size(min = 1, max = 300, message = "확정 진단은 1자 이상 300자 이하여야 합니다.")
    private String assessment;

    @NotBlank
    @Size(min = 1, max = 500, message = "진료 계획은 500자 이하여야 합니다.")
    private String plan;

    @NotBlank
    @Size(min = 1, max = 500, message = "추후 관리는 1자 이상 500자 이하여야 합니다.")
    private String postoperativeCare;
}
