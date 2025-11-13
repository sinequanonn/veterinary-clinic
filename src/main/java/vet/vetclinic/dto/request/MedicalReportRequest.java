package vet.vetclinic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalReportRequest {
    private Long petId;
    private Long vetId;
    private LocalDate reportDate;
    private String chiefComplaint;
    private String assessment;
    private String plan;
    private String postoperativeCare;
}
