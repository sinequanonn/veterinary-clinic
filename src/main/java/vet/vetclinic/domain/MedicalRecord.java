package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medialRecordId;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private String subjective;

    @Column(nullable = false)
    private String objective;

    @Column(nullable = false)
    private String assessment;

    @Column(nullable = false)
    private String plan;

    protected MedicalRecord() {
    }

    public MedicalRecord(LocalDate recordDate, String subjective, String objective, String assessment, String plan) {
        this.recordDate = recordDate;
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.plan = plan;
    }
}
