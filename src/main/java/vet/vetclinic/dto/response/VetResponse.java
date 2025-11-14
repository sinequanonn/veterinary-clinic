package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.Vet;

@Getter
@Builder
public class VetResponse {
    private Long vetId;
    private String vetName;

    public static VetResponse from(Vet vet) {
        return VetResponse.builder()
                .vetId(vet.getVetId())
                .vetName(vet.getVetName())
                .build();
    }
}
