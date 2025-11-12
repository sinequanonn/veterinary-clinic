package vet.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.domain.MedicalReport;
import vet.vetclinic.dto.MedicalRecordResponse;
import vet.vetclinic.dto.MedicalReportRequest;
import vet.vetclinic.dto.MedicalReportResponse;
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
    public ResponseEntity<MedicalReportResponse> create(@RequestBody MedicalReportRequest request) {
        MedicalReport report = medicalReportService.create(
                request.getPetId(),
                request.getVetId(),
                request.getReportDate(),
                request.getChiefComplaint(),
                request.getAssessment(),
                request.getPlan(),
                request.getPostoperativeCare()
        );
        MedicalReportResponse response = MedicalReportResponse.from(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<List<MedicalReportResponse>> findByPetId(@PathVariable Long petId) {
        List<MedicalReportResponse> responses = medicalReportService.findByPetId(petId).stream()
                .map(MedicalReportResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<MedicalReportResponse> findById(@PathVariable Long reportId) {
        MedicalReport report = medicalReportService.findById(reportId);
        MedicalReportResponse response = MedicalReportResponse.from(report);
        return ResponseEntity.ok(response);
    }
}
