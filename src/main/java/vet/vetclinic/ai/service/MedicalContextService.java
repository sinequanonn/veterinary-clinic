package vet.vetclinic.ai.service;

import org.springframework.stereotype.Service;
import vet.vetclinic.ai.domain.MedicalContext;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.exception.BusinessException;
import vet.vetclinic.exception.ErrorCode;
import vet.vetclinic.repository.MedicalRecordRepository;

@Service
public class MedicalContextService {
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalContextService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public MedicalContext makeContext(Long recordId) {
        MedicalRecord record = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REC404));

        return MedicalContext.builder()
                .medicalRecord(record)
                .build();
    }
}
