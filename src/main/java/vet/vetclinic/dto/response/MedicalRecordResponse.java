package vet.vetclinic.dto.response;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalRecord;

import java.time.LocalDate;

@Getter
@Builder
public class MedicalRecordResponse {
    private Long recordId;
    private Long petId;
    private String petName;
    private Long vetId;
    private String vetName;
    private LocalDate recordDate;
    private String subjective;
    private String objective;
    private String assessment;
    private String plan;

    public static MedicalRecordResponse from(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getMedialRecordId(),
                medicalRecord.getPet().getPetId(),
                medicalRecord.getPet().getPetName(),
                medicalRecord.getVet().getVetId(),
                medicalRecord.getVet().getVetName(),
                medicalRecord.getRecordDate(),
                medicalRecord.getSubjective(),
                medicalRecord.getObjective(),
                medicalRecord.getAssessment(),
                medicalRecord.getPlan()
        );
    }
}
