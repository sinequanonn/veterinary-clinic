package vet.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.dto.MedicalRecordRequest;
import vet.vetclinic.dto.MedicalRecordResponse;
import vet.vetclinic.service.MedicalRecordService;

@RestController
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> create(@RequestBody MedicalRecordRequest request) {
        MedicalRecord medicalRecord = medicalRecordService.create(
                request.getVetId(),
                request.getPetId(),
                request.getRecordDate(),
                request.getSubjective(),
                request.getObjective(),
                request.getAssessment(),
                request.getPlan());
        MedicalRecordResponse response = MedicalRecordResponse.from(medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
