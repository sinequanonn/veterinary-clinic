package vet.vetclinic.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.MedicalReport;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MedicalReportRepositoryTest {
    @Autowired
    private MedicalReportRepository medicalReportRepository;

    @Autowired
    private EntityManager entityManager;

    private Pet pet;
    private Vet vet;

    @BeforeEach
    void setUp() {
        pet = new Pet("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8));
        vet = new Vet("박진우");
        entityManager.persist(pet);
        entityManager.persist(vet);
    }

    @Test
    void 진료소견서를_저장한다() {
        //given
        LocalDate reportDate = LocalDate.of(2025, 11, 1);
        String chiefComplaint = "주요 증상 테스트 작성";
        String assessment = "확정 진단 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        String postoperativeCare = "추후 관리 테스트 작성";
        MedicalReport medicalReport = new MedicalReport(pet, vet,
                reportDate,
                chiefComplaint,
                assessment,
                plan,
                postoperativeCare);

        //when
        MedicalReport savedMedicalReport = medicalReportRepository.save(medicalReport);

        //then
        assertThat(savedMedicalReport.getReportDate()).isEqualTo(reportDate);
        assertThat(savedMedicalReport.getChiefComplaint()).isEqualTo(chiefComplaint);
        assertThat(savedMedicalReport.getAssessment()).isEqualTo(assessment);
        assertThat(savedMedicalReport.getPlan()).isEqualTo(plan);
        assertThat(savedMedicalReport.getPostoperativeCare()).isEqualTo(postoperativeCare);
    }
}
