package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@AllArgsConstructor
public class Vet {
    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vetId;

    @Column(nullable = false, length=MAX_NAME_LENGTH)
    private String vetName;

    @Builder.Default
    @OneToMany(mappedBy = "vet")
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "vet")
    List<MedicalReport> medicalReports = new ArrayList<>();

    protected Vet() {
    }

    public Vet(String vetName) {
        validateVetName(vetName);
        this.vetName = vetName;
    }

    private void validateVetName(String vetName) {
        if (vetName == null || vetName.isBlank()) {
            throw new IllegalArgumentException("수의사명은 필수 입력값입니다.");
        }


        if (vetName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("수의사명은 1자 이상 20자 이하여야 합니다.");
        }
    }
}
