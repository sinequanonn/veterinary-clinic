package vet.vetclinic.ai.domain;

import lombok.Builder;
import lombok.Getter;
import vet.vetclinic.domain.MedicalRecord;

@Getter
@Builder
public class MedicalContext {
    private final MedicalRecord medicalRecord;

    public String toText() {
        StringBuilder text = new StringBuilder();
        text.append("===환자정보===\n");
        text.append(String.format("이름: %s\n", medicalRecord.getPet().getPetName()));
        text.append(String.format("품종: %s\n", medicalRecord.getPet().getBreed()));
        text.append(String.format("생년월일: %s\n", medicalRecord.getPet().getBirthDate()));
        text.append(String.format("체중: %.1fkg\n", medicalRecord.getPet().getWeight()));
        text.append(String.format("보호자명: %s\n\n", medicalRecord.getPet().getOwnerName()));

        text.append("===진료기록===\n");
        text.append(String.format("subjective: %s\n", medicalRecord.getSubjective()));
        text.append(String.format("objective: %s\n", medicalRecord.getObjective()));
        text.append(String.format("assessment: %s\n", medicalRecord.getAssessment()));
        text.append(String.format("plan: %s\n", medicalRecord.getPlan()));
        return text.toString();
    }
}
