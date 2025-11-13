package vet.vetclinic.dto.response;

import lombok.Getter;
import vet.vetclinic.domain.Vet;

@Getter
public class VetResponse {
    private Long vetId;
    private String vetName;

    private VetResponse(Long vetId, String vetName) {
        this.vetId = vetId;
        this.vetName = vetName;
    }

    public static VetResponse from(Vet vet) {
        return new VetResponse(vet.getVetId(), vet.getVetName());
    }
}
