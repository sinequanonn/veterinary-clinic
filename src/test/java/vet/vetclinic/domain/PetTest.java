package vet.vetclinic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PetTest {
    @Test
    void 하나의_환자_정보를_생성한다() {
        //given
        String petName = "뽀삐";
        String ownerName = "박진우";
        String breed = "말티즈";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        Pet pet = new Pet(petName, ownerName, breed, weight, birthDate);

        //then
        assertThat(pet.getPetName()).isEqualTo(petName);
        assertThat(pet.getOwnerName()).isEqualTo(ownerName);
        assertThat(pet.getBreed()).isEqualTo(breed);
        assertThat(pet.getWeight()).isEqualTo(weight);
        assertThat(pet.getBirthDate()).isEqualTo(birthDate);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void 환지이름이_null이거나_공백이면_예외를_발생한다(String petName) {
        //given
        String ownerName = "박진우";
        String breed = "말티즈";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        assertThatThrownBy(() -> new Pet(petName, ownerName, breed, weight, birthDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("환자 이름은 1자 이상 20자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 50, 101})
    void 환자이름이_20자_초과하면_예외를_발생한다(int repetition) {
        //given
        String petName = "a".repeat(repetition);
        String ownerName = "박진우";
        String breed = "말티즈";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        assertThatThrownBy(() -> new Pet(petName, ownerName, breed, weight, birthDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("환자 이름은 1자 이상 20자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void 보호자명이_null이거나_공백이면_예외를_발생한다(String ownerName) {
        //given
        String petName = "뽀삐";
        String breed = "말티즈";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        assertThatThrownBy(() -> new Pet(petName, ownerName, breed, weight, birthDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보호자명은 1자 이상 20자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 50, 101})
    void 보호자명이_20자_초과하면_예외를_발생한다(int repetition) {
        //given
        String petName = "뽀삐";
        String ownerName = "a".repeat(repetition);
        String breed = "말티즈";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        assertThatThrownBy(() -> new Pet(petName, ownerName, breed, weight, birthDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보호자명은 1자 이상 20자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void 품종이_null이거나_공백이면_예외를_발생한다(String breed) {
        //given
        String petName = "뽀삐";
        String ownerName = "박진우";
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        assertThatThrownBy(() -> new Pet(petName, ownerName, breed, weight, birthDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("품종은 1자 이상 20자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 50, 101})
    void 품종이_20자_초과하면_예외를_발생한다(int repetition) {
        //given
        String petName = "뽀삐";
        String ownerName = "박진우";
        String breed = "a".repeat(repetition);
        double weight = 3.5;
        LocalDate birthDate = LocalDate.of(2025, 11, 8);

        //when
        assertThatThrownBy(() -> new Pet(petName, ownerName, breed, weight, birthDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("품종은 1자 이상 20자 이하여야 합니다.");
    }
}
