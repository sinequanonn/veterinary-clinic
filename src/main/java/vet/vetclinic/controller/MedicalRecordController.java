package vet.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.dto.MedicalRecordRequest;
import vet.vetclinic.dto.MedicalRecordResponse;
import vet.vetclinic.service.MedicalRecordService;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<MedicalRecordResponse>> findByPetId(@PathVariable Long petId) {
        List<MedicalRecordResponse> responses = medicalRecordService.findByPetId(petId).stream()
                .map(MedicalRecordResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponse> findById(@PathVariable Long recordId) {
        MedicalRecord record = medicalRecordService.findById(recordId);
        MedicalRecordResponse response = MedicalRecordResponse.from(record);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponse> update(
            @PathVariable Long recordId,
            @RequestBody MedicalRecordRequest request) {
        MedicalRecord medicalRecord = medicalRecordService.update(
                recordId,
                request.getRecordDate(),
                request.getSubjective(),
                request.getObjective(),
                request.getAssessment(),
                request.getPlan()
        );
        MedicalRecordResponse response = MedicalRecordResponse.from(medicalRecord);
        return ResponseEntity.ok(response);
    }
}
