package vet.vetclinic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Test
    void 환자가_있는_경우_모든_환자의_목록을_조회한다() {
        //given
        petRepository.save(new Pet("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8)));
        petRepository.save(new Pet("순수", "김정나", "비숑", 2.5, LocalDate.of(2023, 11, 8)));
        petRepository.save(new Pet("면봉", "박지아", "웰시코기", 4.5, LocalDate.of(2022, 11, 8)));

        //when
        List<Pet> pets = petRepository.findAll();

        //then
        assertThat(pets).hasSize(3);
        assertThat(pets)
                .extracting("petName")
                .containsExactly("뽀삐", "순수", "면봉");
    }

    @Test
    void 환자가_없는_경우_빈리스트를_반환한다() {
        //when
        List<Pet> pets = petRepository.findAll();

        //then
        assertThat(pets).isEmpty();
    }

    @Test
    void 환자번호로_하나의_환자를_조회한다() {
        //given
        Pet savedPet = petRepository.save(new Pet("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8)));

        //when
        Optional<Pet> foundPet = petRepository.findById(savedPet.getPetId());

        //then
        assertThat(foundPet.get().getPetName()).isEqualTo("뽀삐");
        assertThat(foundPet.get().getOwnerName()).isEqualTo("박진우");
        assertThat(foundPet.get().getBreed()).isEqualTo("말티즈");
    }
}
