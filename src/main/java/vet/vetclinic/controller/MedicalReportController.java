package vet.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vet.vetclinic.domain.MedicalReport;
import vet.vetclinic.dto.MedicalReportRequest;
import vet.vetclinic.dto.MedicalReportResponse;
import vet.vetclinic.service.MedicalReportService;

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
}
