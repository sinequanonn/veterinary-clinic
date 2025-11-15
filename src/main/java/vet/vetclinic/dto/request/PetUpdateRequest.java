package vet.vetclinic.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetUpdateRequest {
    @NotBlank
    @Size(min=1, max=20, message="환자 이름은 1자 이상 20자 이하여야 합니다.")
    private String petName;

    @NotBlank
    @Size(min=1, max=20, message="보호자명은 1자 이상 20자 이하여야 합니다.")
    private String ownerName;

    @NotBlank
    @Size(min=1, max=20, message="품종은 1자 이상 20자 이하여야 합니다.")
    private String breed;

    @NotNull
    @Positive(message = "체중은 0보다 커야 합니다.")
    private double weight;

    @NotNull
    @PastOrPresent(message = "날짜는 오늘 이전 날짜여야 합니다.")
    private LocalDate birthDate;
}
