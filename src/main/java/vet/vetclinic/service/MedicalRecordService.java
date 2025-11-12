package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.repository.MedicalRecordRepository;
import vet.vetclinic.repository.PetRepository;
import vet.vetclinic.repository.VetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PetRepository petRepository, VetRepository vetRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
    }

    @Transactional
    public MedicalRecord create(Long vetId, Long petId, LocalDate recordDate,
                                String subjective, String objective, String assessment, String plan) {
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수의사입니다."));
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환자입니다."));

        MedicalRecord medicalRecord = new MedicalRecord(pet, vet, recordDate, subjective, objective, assessment, plan);
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord findById(Long recordId) {
        return medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 진료 기록입니다."));
    }

    public List<MedicalRecord> findByPetId(Long petId) {
        return medicalRecordRepository.findByPet_PetId(petId);
    }

    @Transactional
    public void update(Long recordId, LocalDate recordDate,
                       String subjective, String objective, String assessment, String plan) {
        MedicalRecord foundMedicalRecord = findById(recordId);
        foundMedicalRecord.updateMedicalRecord(recordDate, subjective, objective, assessment, plan);
    }

    @Transactional
    public void delete(Long recordId) {
        medicalRecordRepository.deleteById(recordId);
    }
}
