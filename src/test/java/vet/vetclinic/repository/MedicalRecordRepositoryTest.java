package vet.vetclinic.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MedicalRecordRepositoryTest {
    @Autowired
    private MedicalRepository medicalRepository;

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
        MedicalRecord savedRecord = medicalRepository.save(medicalRecord);

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
}
