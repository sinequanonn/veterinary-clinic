package vet.vetclinic.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicalRecordTest {
    @Test
    void 하나의_진료_기록을_생성한다() {
        //given
        LocalDate recordDate = LocalDate.of(2025, 11, 10);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "급성 테스트 작성";
        String plan = "테스트 검사 계획";

        //when
        MedicalRecord medicalRecord = new MedicalRecord(recordDate, subjective, objective, assessment, plan);

        //then
        assertThat(medicalRecord.getRecordDate()).isEqualTo(recordDate);
        assertThat(medicalRecord.getSubjective()).isEqualTo(subjective);
        assertThat(medicalRecord.getObjective()).isEqualTo(objective);
        assertThat(medicalRecord.getAssessment()).isEqualTo(assessment);
        assertThat(medicalRecord.getPlan()).isEqualTo(plan);
    }
}
