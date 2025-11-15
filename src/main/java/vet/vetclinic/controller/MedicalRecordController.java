package vet.vetclinic.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.dto.request.MedicalRecordCreateRequest;
import vet.vetclinic.dto.request.MedicalRecordUpdateRequest;
import vet.vetclinic.dto.response.MedicalRecordResponse;
import vet.vetclinic.dto.response.MedicalRecordOfPetResponse;
import vet.vetclinic.service.MedicalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@RequestBody @Valid MedicalRecordCreateRequest request) {
        MedicalRecordResponse response = medicalRecordService.createMedicalRecord(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<MedicalRecordOfPetResponse>> findByPetId(@PathVariable Long petId) {
        List<MedicalRecordOfPetResponse> responses = medicalRecordService.findByPetId(petId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponse> findById(@PathVariable Long recordId) {
        MedicalRecordResponse response = medicalRecordService.findById(recordId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(
            @PathVariable Long recordId,
            @RequestBody @Valid MedicalRecordUpdateRequest request) {
        MedicalRecordResponse response = medicalRecordService.updateMedicalRecord(recordId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long recordId) {
        medicalRecordService.deleteMedicalRecord(recordId);
        return ResponseEntity.noContent().build();
    }
}
