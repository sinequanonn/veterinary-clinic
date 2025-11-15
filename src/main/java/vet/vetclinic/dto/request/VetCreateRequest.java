package vet.vetclinic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import vet.vetclinic.domain.Vet;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VetCreateRequest {
    @NotBlank(message = "수의사명은 필수 입력값입니다.")
    @Size(min = 1, max = 20, message = "수의사명은 1자 이상 20자 이하여야 합니다.")
    private String vetName;

    public VetCreateRequest(String vetName) {
        this.vetName = vetName;
    }

    public Vet toEntity() {
        return Vet.builder()
                .vetName(vetName)
                .build();
    }
}
