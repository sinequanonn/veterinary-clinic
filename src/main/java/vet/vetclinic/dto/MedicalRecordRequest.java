package vet.vetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordRequest {
    private Long petId;
    private Long vetId;
    private LocalDate recordDate;
    private String subjective;
    private String objective;
    private String assessment;
    private String plan;
}
