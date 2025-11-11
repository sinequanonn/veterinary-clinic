package vet.vetclinic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.Pet;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PetRepositoryTest {
    @Autowired
    private PetRepository petRepository;

    @Test
    void 하나의_환자를_저장한다() {
        //given
        String petName = "뽀삐";
        String ownerName = "박진우";
        String breed = "말티즈";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);
        Pet pet = new Pet(petName, ownerName, breed, weight, birthDate);

        //when
        Pet savedPet = petRepository.save(pet);

        //then
        assertThat(savedPet.getPetName()).isEqualTo(petName);
        assertThat(savedPet.getOwnerName()).isEqualTo(ownerName);
        assertThat(savedPet.getBreed()).isEqualTo(breed);
        assertThat(savedPet.getWeight()).isEqualTo(weight);
        assertThat(savedPet.getBirthDate()).isEqualTo(birthDate);
    }
}
