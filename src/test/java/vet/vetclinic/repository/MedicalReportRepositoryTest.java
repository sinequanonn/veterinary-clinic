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
import java.util.List;
import java.util.Optional;

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

    @Test
    void 하나의_환자의_모든_진료소견서를_조회한다() {
        //given
        MedicalReport medicalReport1 = new MedicalReport(pet, vet,
                LocalDate.of(2025, 11, 1),
                "주요 증상 테스트 작성1",
                "확정 진단 테스트 작성1",
                "진료 계획 테스트 작성1",
                "추후 관리 테스트 작성1");
        MedicalReport medicalReport2 = new MedicalReport(pet, vet,
                LocalDate.of(2025, 11, 2),
                "주요 증상 테스트 작성2",
                "확정 진단 테스트 작성2",
                "진료 계획 테스트 작성2",
                "추후 관리 테스트 작성2");
        MedicalReport medicalReport3 = new MedicalReport(pet, vet,
                LocalDate.of(2025, 11, 3),
                "주요 증상 테스트 작성3",
                "확정 진단 테스트 작성3",
                "진료 계획 테스트 작성3",
                "추후 관리 테스트 작성3");
        medicalReportRepository.save(medicalReport1);
        medicalReportRepository.save(medicalReport2);
        medicalReportRepository.save(medicalReport3);

        //when
        List<MedicalReport> medicalReports = medicalReportRepository.findByPet_PetId(pet.getPetId());

        //then
        assertThat(medicalReports).hasSize(3);
        assertThat(medicalReports).allMatch(record ->
                record.getPet().getPetId().equals(pet.getPetId()));
    }

    @Test
    void 진료소견서번호로_하나의_진료_소견서를_조회한다() {
        //given
        MedicalReport medicalReport = new MedicalReport(pet, vet,
                LocalDate.of(2025, 11, 1),
                "주요 증상 테스트 작성1",
                "확정 진단 테스트 작성1",
                "진료 계획 테스트 작성1",
                "추후 관리 테스트 작성1");
        MedicalReport savedMedicalReport = medicalReportRepository.save(medicalReport);

        //when
        Optional<MedicalReport> foundMedicalReport = medicalReportRepository.findById(savedMedicalReport.getMedicalReportId());

        //then
        assertThat(foundMedicalReport).isPresent();
        assertThat(foundMedicalReport.get().getMedicalReportId()).isEqualTo(savedMedicalReport.getMedicalReportId());
        assertThat(foundMedicalReport.get().getChiefComplaint()).isEqualTo(savedMedicalReport.getChiefComplaint());
    }

    @Test
    void 진료소견서를_수정한다() {
        //given
        MedicalReport medicalReport = new MedicalReport(pet, vet,
                LocalDate.of(2025, 11, 1),
                "주요 증상 테스트 작성1",
                "확정 진단 테스트 작성1",
                "진료 계획 테스트 작성1",
                "추후 관리 테스트 작성1");
        MedicalReport savedMedicalReport = medicalReportRepository.save(medicalReport);

        //when
        MedicalReport foundMedicalReport = medicalReportRepository.findById(savedMedicalReport.getMedicalReportId()).orElseThrow();
        foundMedicalReport.update(
                LocalDate.of(2022, 11, 10),
                "주요 증상 테스트 수정",
                "확정 진단 테스트 수정",
                "진료 계획 테스트 수정",
                "추후 관리 테스트 수정");
        MedicalReport updatedMedicalReport = medicalReportRepository.findById(foundMedicalReport.getMedicalReportId()).orElseThrow();

        //then
        assertThat(updatedMedicalReport.getReportDate()).isEqualTo(LocalDate.of(2022, 11, 10));
        assertThat(updatedMedicalReport.getChiefComplaint()).isEqualTo("주요 증상 테스트 수정");
        assertThat(updatedMedicalReport.getAssessment()).isEqualTo("확정 진단 테스트 수정");
        assertThat(updatedMedicalReport.getPlan()).isEqualTo("진료 계획 테스트 수정");
        assertThat(updatedMedicalReport.getPostoperativeCare()).isEqualTo("추후 관리 테스트 수정");
    }

    @Test
    void 진료소견서를_삭제한다() {
        //given
        MedicalReport medicalReport = new MedicalReport(pet, vet,
                LocalDate.of(2025, 11, 1),
                "주요 증상 테스트 작성1",
                "확정 진단 테스트 작성1",
                "진료 계획 테스트 작성1",
                "추후 관리 테스트 작성1");
        MedicalReport savedMedicalReport = medicalReportRepository.save(medicalReport);

        //when
        medicalReportRepository.deleteById(savedMedicalReport.getMedicalReportId());
        Optional<MedicalReport> foundMedicalReport = medicalReportRepository.findById(savedMedicalReport.getMedicalReportId());

        //then
        assertThat(foundMedicalReport).isEmpty();
    }
}
