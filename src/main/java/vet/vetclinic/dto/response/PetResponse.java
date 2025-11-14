package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.Pet;

import java.time.LocalDate;

@Getter
@Builder
public class PetResponse {
    private Long petId;
    private String petName;
    private String ownerName;
    private String breed;
    private double weight;
    private LocalDate birthDate;

    public static PetResponse from(Pet pet) {
        return PetResponse.builder()
                .petId(pet.getPetId())
                .petName(pet.getPetName())
                .ownerName(pet.getOwnerName())
                .breed(pet.getBreed())
                .weight(pet.getWeight())
                .birthDate(pet.getBirthDate())
                .build();
    }
}
