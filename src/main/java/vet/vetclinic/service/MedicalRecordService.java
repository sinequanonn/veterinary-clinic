package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.request.MedicalRecordCreateRequest;
import vet.vetclinic.dto.request.MedicalRecordUpdateRequest;
import vet.vetclinic.dto.response.MedicalRecordResponse;
import vet.vetclinic.dto.response.MedicalRecordOfPetResponse;
import vet.vetclinic.exception.BusinessException;
import vet.vetclinic.exception.ErrorCode;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.PET404));
        Vet vet = vetRepository.findById(request.getVetId())
                .orElseThrow(() -> new BusinessException(ErrorCode.VET404));

        return MedicalRecordResponse.from(medicalRecordRepository.save(request.toEntity(pet, vet)));
    }

    public MedicalRecordResponse findById(Long recordId) {
        return medicalRecordRepository.findById(recordId)
                .map(MedicalRecordResponse::from)
                .orElseThrow(() -> new BusinessException(ErrorCode.REC404));
    }

    public List<MedicalRecordOfPetResponse> findByPetId(Long petId) {
        List<MedicalRecord> records = medicalRecordRepository.findByPet_PetId(petId);

        return records.stream()
                .map(MedicalRecordOfPetResponse::from)
                .toList();
    }

    @Transactional
    public MedicalRecordResponse updateMedicalRecord(Long recordId, MedicalRecordUpdateRequest request) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REC404));

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
            throw new BusinessException(ErrorCode.REC404);
        }
        medicalRecordRepository.deleteById(recordId);
    }
}
