package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalRecord;

import java.time.LocalDate;

@Getter
@Builder
public class MedicalRecordOfPetResponse {
    private Long recordId;
    private LocalDate recordDate;
    private String petName;
    private String assessment;

    public static MedicalRecordOfPetResponse from(MedicalRecord record) {
        return MedicalRecordOfPetResponse.builder()
                .recordId(record.getMedialRecordId())
                .recordDate(record.getRecordDate())
                .petName(record.getPet().getPetName())
                .assessment(record.getAssessment())
                .build();
    }
}
