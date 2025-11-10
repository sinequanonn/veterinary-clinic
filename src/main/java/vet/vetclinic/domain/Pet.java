package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class Pet {
    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String petName;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private LocalDate birthDate;

    protected Pet() {
    }

    public Pet(String petName, String ownerName, String breed, double weight, LocalDate birthDate) {
        validatePetName(petName);

        this.petName = petName;
        this.ownerName = ownerName;
        this.breed = breed;
        this.weight = weight;
        this.birthDate = birthDate;
    }

    private void validatePetName(String petName) {
        if (petName == null || petName.isBlank()) {
            throw new IllegalArgumentException("환자 이름은 1자 이상 20자 이하여야 합니다.");
        }

        if (petName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("환자 이름은 1자 이상 20자 이하여야 합니다.");
        }
    }
}
