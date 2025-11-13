package vet.vetclinic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vet.vetclinic.domain.Pet;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PetResponse {
    private Long petId;
    private String petName;
    private String ownerName;
    private String breed;
    private double weight;
    private LocalDate birthDate;

    public static PetResponse from(Pet pet) {
        return new PetResponse(
                pet.getPetId(),
                pet.getPetName(),
                pet.getOwnerName(),
                pet.getBreed(),
                pet.getWeight(),
                pet.getBirthDate()
        );
    }
}
