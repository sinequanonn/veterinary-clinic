package vet.vetclinic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalRecord;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class MedicalRecordListOfPetResponse {
    Long recordId;
    LocalDate recordDate;
    String petName;
    String assessment;

    public static MedicalRecordListOfPetResponse from(MedicalRecord record) {
        return MedicalRecordListOfPetResponse.builder()
                .recordId(record.getMedialRecordId())
                .recordDate(record.getRecordDate())
                .petName(record.getPet().getPetName())
                .assessment(record.getAssessment())
                .build();
    }
}
