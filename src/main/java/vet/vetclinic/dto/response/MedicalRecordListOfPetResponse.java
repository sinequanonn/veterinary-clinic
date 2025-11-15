package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalRecord;

import java.time.LocalDate;

@Getter
@Builder
public class MedicalRecordListOfPetResponse {
    private Long recordId;
    private LocalDate recordDate;
    private String petName;
    private String assessment;

    public static MedicalRecordListOfPetResponse from(MedicalRecord record) {
        return MedicalRecordListOfPetResponse.builder()
                .recordId(record.getMedialRecordId())
                .recordDate(record.getRecordDate())
                .petName(record.getPet().getPetName())
                .assessment(record.getAssessment())
                .build();
    }
}
