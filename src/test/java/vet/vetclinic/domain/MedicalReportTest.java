package vet.vetclinic.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicalReportTest {
    @Test
    void 하나의_진료_소견서를_생성한다() {
        //given
        Pet pet = createPet();
        Vet vet = createVet();
        LocalDate reportDate = LocalDate.of(2025, 11, 9);
        String chiefComplaint = "";
        String assessment = "";
        String plan = "";
        String postoperativeCare = "";

        //when
        MedicalReport medicalReport = new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare);

        //then
        assertThat(medicalReport.getReportDate()).isEqualTo(reportDate);
        assertThat(medicalReport.getChiefComplaint()).isEqualTo(chiefComplaint);
        assertThat(medicalReport.getAssessment()).isEqualTo(assessment);
        assertThat(medicalReport.getPlan()).isEqualTo(plan);
        assertThat(medicalReport.getPostoperativeCare()).isEqualTo(postoperativeCare);

    }

    private Pet createPet() {
        return new Pet("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8));
    }

    private Vet createVet() {
        return new Vet("박진우");
    }
}
