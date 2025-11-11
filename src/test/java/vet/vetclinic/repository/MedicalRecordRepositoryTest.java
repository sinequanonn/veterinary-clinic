package vet.vetclinic.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MedicalRecordRepositoryTest {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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
    void 하나의_진료_기록을_저장한다() {
        //given
        LocalDate date = LocalDate.of(2025, 11, 11);
        String subjective = "주관적 판단 테스트 작성";
        String objective = "객관적 판단 테스트 작성";
        String assessment = "확정 진단명 테스트 작성";
        String plan = "진료 계획 테스트 작성";
        MedicalRecord medicalRecord = new MedicalRecord(pet, vet, date, subjective, objective, assessment, plan);

        //when
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);

        //then
        assertThat(savedRecord).isNotNull();
        assertThat(savedRecord.getMedialRecordId()).isNotNull();
        assertThat(savedRecord.getPet().getPetId()).isEqualTo(pet.getPetId());
        assertThat(savedRecord.getVet().getVetId()).isEqualTo(vet.getVetId());
        assertThat(savedRecord.getRecordDate()).isEqualTo(date);
        assertThat(savedRecord.getSubjective()).isEqualTo(subjective);
        assertThat(savedRecord.getObjective()).isEqualTo(objective);
        assertThat(savedRecord.getAssessment()).isEqualTo(assessment);
        assertThat(savedRecord.getPlan()).isEqualTo(plan);
    }

    @Test
    void 하나의_환자_모든_진료기록을_조회한다() {
        //given
        MedicalRecord medicalRecord1 = new MedicalRecord(pet, vet,
                LocalDate.of(2025, 11, 1),
                "주관적 판단 테스트 작성1",
                "객관적 판단 테스트 작성1",
                "확정 진단명 테스트 작성1",
                "진료 계획 테스트 작성1");
        MedicalRecord medicalRecord2 = new MedicalRecord(pet, vet,
                LocalDate.of(2025, 11, 5),
                "주관적 판단 테스트 작성2",
                "객관적 판단 테스트 작성2",
                "확정 진단명 테스트 작성2",
                "진료 계획 테스트 작성2");
        MedicalRecord medicalRecord3 = new MedicalRecord(pet, vet,
                LocalDate.of(2025, 11, 10),
                "주관적 판단 테스트 작성3",
                "객관적 판단 테스트 작성3",
                "확정 진단명 테스트 작성3",
                "진료 계획 테스트 작성3");
        medicalRecordRepository.save(medicalRecord1);
        medicalRecordRepository.save(medicalRecord2);
        medicalRecordRepository.save(medicalRecord3);

        //when
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPet_PetId((pet.getPetId()));

        //then
        assertThat(medicalRecords).hasSize(3);
        assertThat(medicalRecords).allMatch(record ->
                record.getPet().getPetId().equals(pet.getPetId()));
    }

    @Test
    void 진료기록번호로_진료기록을_상세_조회한다() {
        //given
        MedicalRecord medicalRecord1 = new MedicalRecord(pet, vet,
                LocalDate.of(2025, 11, 1),
                "주관적 판단 테스트 작성1",
                "객관적 판단 테스트 작성1",
                "확정 진단명 테스트 작성1",
                "진료 계획 테스트 작성1");
    }
}
