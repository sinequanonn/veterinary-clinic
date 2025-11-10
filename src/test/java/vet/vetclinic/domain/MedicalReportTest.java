package vet.vetclinic.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MedicalReportTest {
    @Test
    void 하나의_진료_소견서를_생성한다() {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요 증상 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        MedicalReport medicalReport = new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare);

        //then
        assertThat(medicalReport.getReportDate()).isEqualTo(reportDate);
        assertThat(medicalReport.getChiefComplaint()).isEqualTo(chiefComplaint);
        assertThat(medicalReport.getAssessment()).isEqualTo(assessment);
        assertThat(medicalReport.getPlan()).isEqualTo(plan);
        assertThat(medicalReport.getPostoperativeCare()).isEqualTo(postoperativeCare);
    }

    @Test
    void 진료소견서_날짜가_오늘보다_미래인_경우_예외를_발생한다() {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.now().plusDays(1);
        String chiefComplaint = "주요 증상 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 오늘 이전 날짜여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 주요증상이_null이거나_공백이면_예외를_발생한다(String chiefComplaint) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주요 증상은 1자 이상 300자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 302, 1300})
    void 주요증상_길이가_300초과이면_예외를_발생한다(int repetition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "a".repeat(repetition);
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주요 증상은 1자 이상 300자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 확정진단명이_null이거나_공백이면_예외를_발생한다(String assessment) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요 증상 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확정 진단은 1자 이상 300자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 302, 1300})
    void 확정진단_길이가_300초과이면_예외를_발생한다(int repetition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요증상 진단명 테스트 작성";
        String assessment = "a".repeat(repetition);
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("확정 진단은 1자 이상 300자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 진료계획이_null이거나_공백이면_예외를_발생한다(String plan) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요 증상 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("치료 계획은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {501, 502, 1300})
    void 진료계획_길이가_500초과이면_예외를_발생한다(int repetition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요증상 진단명 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String plan = "a".repeat(repetition);
        String postoperativeCare = "추후 관리 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("치료 계획은 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void 추후관리가_null이거나_공백이면_예외를_발생한다(String postoperativeCare) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요 증상 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("추후 관리는 1자 이상 500자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {501, 502, 1300})
    void 추후관리_길이가_500초과이면_예외를_발생한다(int repetition) {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "주요증상 진단명 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "a".repeat(repetition);

        //when
        assertThatThrownBy(() -> new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("추후 관리는 1자 이상 500자 이하여야 합니다.");
    }

    private Pet createPet() {
        return new Pet("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8));
    }

    private Vet createVet() {
        return new Vet("박진우");
    }
}
