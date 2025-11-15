package vet.vetclinic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalReport;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class MedicalReportListOfPetResponse {
    private Long recordId;
    private LocalDate reportDate;
    private String vetName;
    private String assessment;

    public static MedicalReportListOfPetResponse from(MedicalReport report) {
        return MedicalReportListOfPetResponse.builder()
                .recordId(report.getMedicalReportId())
                .reportDate(report.getReportDate())
                .vetName(report.getVet().getVetName())
                .assessment(report.getAssessment())
                .build();
    }
}
