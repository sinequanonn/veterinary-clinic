package vet.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {
    private String petName;
    private String ownerName;
    private String breed;
    private double weight;
    private LocalDate birthDate;
}
