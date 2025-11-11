package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Pet {
    private static final int MAX_NAME_LENGTH = 20;
    private static final double MIN_WEIGHT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String petName;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String ownerName;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String breed;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "pet")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    protected Pet() {
    }

    public Pet(String petName, String ownerName, String breed, double weight, LocalDate birthDate) {
        validatePetName(petName);
        validateOwnerName(ownerName);
        validateBreed(breed);
        validateWeight(weight);
        validateBirthDate(birthDate);

        this.petName = petName;
        this.ownerName = ownerName;
        this.breed = breed;
        this.weight = weight;
        this.birthDate = birthDate;
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("날짜는 오늘 이전 날짜여야 합니다.");
        }
    }

    private void validateWeight(double weight) {
        if (weight <= MIN_WEIGHT) {
            throw new IllegalArgumentException("체중은 0보다 커야 합니다.");
        }
    }

    private void validateBreed(String breed) {
        if (breed == null || breed.isBlank()) {
            throw new IllegalArgumentException("품종은 1자 이상 20자 이하여야 합니다.");
        }

        if (breed.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("품종은 1자 이상 20자 이하여야 합니다.");
        }
    }

    private void validateOwnerName(String ownerName) {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("보호자명은 1자 이상 20자 이하여야 합니다.");
        }

        if (ownerName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("보호자명은 1자 이상 20자 이하여야 합니다.");
        }
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
