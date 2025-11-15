package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalReport;

import java.time.LocalDate;

@Getter
@Builder
public class MedicalReportOfPetResponse {
    private Long reportId;
    private LocalDate reportDate;
    private String vetName;
    private String assessment;

    public static MedicalReportOfPetResponse from(MedicalReport report) {
        return MedicalReportOfPetResponse.builder()
                .reportId(report.getMedicalReportId())
                .reportDate(report.getReportDate())
                .vetName(report.getVet().getVetName())
                .assessment(report.getAssessment())
                .build();
    }
}
