package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.request.MedicalRecordCreateRequest;
import vet.vetclinic.dto.request.MedicalRecordUpdateRequest;
import vet.vetclinic.dto.response.MedicalRecordResponse;
import vet.vetclinic.dto.response.MedicalRecordListOfPetResponse;
import vet.vetclinic.repository.MedicalRecordRepository;
import vet.vetclinic.repository.PetRepository;
import vet.vetclinic.repository.VetRepository;

import java.util.List;

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
    public MedicalRecordResponse createMedicalRecord(MedicalRecordCreateRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환자입니다."));
        Vet vet = vetRepository.findById(request.getVetId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수의사입니다."));

        return MedicalRecordResponse.from(medicalRecordRepository.save(request.toEntity(pet, vet)));
    }

    public MedicalRecordResponse findById(Long recordId) {
        return medicalRecordRepository.findById(recordId)
                .map(MedicalRecordResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 진료 기록입니다."));
    }

    public List<MedicalRecordListOfPetResponse> findByPetId(Long petId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPet_PetId(petId);

        return records.stream()
                .map(MedicalRecordListOfPetResponse::from)
                .toList();
    }

    @Transactional
    public MedicalRecordResponse updateMedicalRecord(Long recordId, MedicalRecordUpdateRequest request) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 진료 기록입니다."));

        medicalRecord.updateMedicalRecord(
                request.getRecordDate(),
                request.getSubjective(),
                request.getObjective(),
                request.getAssessment(),
                request.getPlan()
        );

        return MedicalRecordResponse.from(medicalRecord);
    }

    @Transactional
    public void deleteMedicalRecord(Long recordId) {
        if (!medicalRecordRepository.existsById(recordId)) {
            throw new IllegalArgumentException("존재하지 않는 진료 기록입니다.");
        }
        medicalRecordRepository.deleteById(recordId);
    }
}
