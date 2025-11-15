package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalReport;

import java.time.LocalDate;

@Getter
@Builder
public class MedicalReportResponse {
    private Long reportId;
    private Long petId;
    private String petName;
    private Long vetId;
    private String vetName;
    private LocalDate reportDate;
    private String chiefComplaint;
    private String assessment;
    private String plan;
    private String postoperativeCare;

    public static MedicalReportResponse from(MedicalReport report) {
        return new MedicalReportResponse(
                report.getMedicalReportId(),
                report.getPet().getPetId(),
                report.getPet().getPetName(),
                report.getVet().getVetId(),
                report.getVet().getVetName(),
                report.getReportDate(),
                report.getChiefComplaint(),
                report.getAssessment(),
                report.getPlan(),
                report.getPostoperativeCare()
        );
    }
}
