package vet.vetclinic.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.ai.dto.GeneratedMedicalReportResponse;
import vet.vetclinic.ai.service.MedicalReportAIService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ai/medical-reports")
public class MedicalReportAIController {
    private final MedicalReportAIService medicalReportAIService;

    public MedicalReportAIController(MedicalReportAIService medicalReportAIService) {
        this.medicalReportAIService = medicalReportAIService;
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<GeneratedMedicalReportResponse> generatedMedicalReport(@PathVariable Long recordId) {
        GeneratedMedicalReportResponse response = medicalReportAIService.generatedMedicalReport(recordId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
