package vet.vetclinic.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.dto.request.MedicalReportCreateRequest;
import vet.vetclinic.dto.request.MedicalReportUpdateRequest;
import vet.vetclinic.dto.response.MedicalReportOfPetResponse;
import vet.vetclinic.dto.response.MedicalReportResponse;
import vet.vetclinic.service.MedicalReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-reports")
public class MedicalReportController {
    private final MedicalReportService medicalReportService;

    public MedicalReportController(MedicalReportService medicalReportService) {
        this.medicalReportService = medicalReportService;
    }

    @PostMapping
    public ResponseEntity<MedicalReportResponse> createMedicalReport(
            @RequestBody @Valid MedicalReportCreateRequest request) {
        MedicalReportResponse response = medicalReportService.createMedicalReport(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<MedicalReportResponse> findById(@PathVariable Long reportId) {
        MedicalReportResponse response = medicalReportService.findById(reportId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<MedicalReportOfPetResponse>> findByPetId(@PathVariable Long petId) {
        List<MedicalReportOfPetResponse> responses = medicalReportService.findByPetId(petId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<MedicalReportResponse> updateMedicalReport(
            @PathVariable Long reportId,
            @RequestBody @Valid MedicalReportUpdateRequest request) {
        MedicalReportResponse response = medicalReportService.updateMedicalReport(reportId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deleteMedicalReport(@PathVariable Long reportId) {
        medicalReportService.deleteMedicalReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
