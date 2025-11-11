package vet.vetclinic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicalRecordTest {
    @Test
    void 하나의_진료_기록을_생성한다() {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when
        MedicalRecord medicalRecord = new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan);

        //then
        assertThat(medicalRecord.getRecordDate()).isEqualTo(recordDate);
        assertThat(medicalRecord.getSubjective()).isEqualTo(subjective);
        assertThat(medicalRecord.getObjective()).isEqualTo(objective);
        assertThat(medicalRecord.getAssessment()).isEqualTo(assessment);
        assertThat(medicalRecord.getPlan()).isEqualTo(plan);
    }

    @Test
    void 작성날짜가_오늘보다_미래인_경우_예외를_발생한다() {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.now().plusDays(1);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 오늘 이전 날짜여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 주관적판단이_null이거나_공백이면_예외를_발생한다(String subjective) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주관적 판단은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {501, 1500, 2000})
    void 주관적판단이_500자_초과이면_예외를_발생한다(int repeition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "a".repeat(repeition);
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주관적 판단은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 객관적판단이_null이거나_공백이면_예외를_발생한다(String objective) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("객관적 판단은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {501, 1500, 2000})
    void 객관적판단이_500자_초과이면_예외를_발생한다(int repeition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "a".repeat(repeition);
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("객관적 판단은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 확정잔단명이_null이거나_공백이면_예외를_발생한다(String assessment) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확정 진단은 1자 이상 200자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {201, 202, 300})
    void 확정진단명이_200자_초과이면_예외를_발생한다(int repeition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "a".repeat(repeition);
        String plan = "테스트 검사 계획";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확정 진단은 1자 이상 200자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 진료계획이_null이거나_공백이면_예외를_발생한다(String plan) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 검사 작성";

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료 계획은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {501, 502, 1500})
    void 진료계획이_500자_초과이면_예외를_발생한다(int repetition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "a".repeat(repetition);

        //when&then
        Assertions.assertThatThrownBy(() -> new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료 계획은 1자 이상 500자 이하여야 합니다.");
    }

    private Pet createPet() {
        return new Pet("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8));
    }

    private Vet createVet() {
        return new Vet("박진우");
    }
}
