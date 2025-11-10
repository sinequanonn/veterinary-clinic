package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class MedicalRecord {
    public static final int MAX_SUBJECTIVE_LENGTH = 500;
    public static final int MAX_OBJECTIVE_LENGTH = 500;
    public static final int MAX_ASSESSMENT_LENGTH = 200;
    public static final int MAX_PLAN_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medialRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

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

    public MedicalRecord(Pet pet, Vet vet, LocalDate recordDate, String subjective, String objective, String assessment, String plan) {
        validateRecordDate(recordDate);
        validateSubjective(subjective);
        validateObjective(objective);
        validateAssessment(assessment);
        validatePlan(plan);

        this.pet = pet;
        this.vet = vet;
        this.recordDate = recordDate;
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.plan = plan;
    }

    private void validatePlan(String plan) {
        if (plan == null || plan.isBlank()) {
            throw new IllegalArgumentException("진료 계획은 1자 이상 500자 이하여야 합니다.");
        }

        if (plan.length() > MAX_PLAN_LENGTH) {
            throw new IllegalArgumentException("진료 계획은 1자 이상 500자 이하여야 합니다.");
        }
    }

    private void validateAssessment(String assessment) {
        if (assessment == null || assessment.isBlank()) {
            throw new IllegalArgumentException("확정 진단은 1자 이상 200자 이하여야 합니다.");
        }

        if (assessment.length() > MAX_ASSESSMENT_LENGTH) {
            throw new IllegalArgumentException("확정 진단은 1자 이상 200자 이하여야 합니다.");
        }
    }

    private void validateObjective(String objective) {
        if (objective == null || objective.isBlank()) {
            throw new IllegalArgumentException("객관적 판단은 1자 이상 500자 이하여야 합니다.");
        }

        if (objective.length() > MAX_OBJECTIVE_LENGTH) {
            throw new IllegalArgumentException("객관적 판단은 1자 이상 500자 이하여야 합니다.");
        }
    }

    private void validateSubjective(String subjective) {
        if (subjective == null || subjective.isBlank()) {
            throw new IllegalArgumentException("주관적 판단은 1자 이상 500자 이하여야 합니다.");
        }

        if (subjective.length() > MAX_SUBJECTIVE_LENGTH) {
            throw new IllegalArgumentException("주관적 판단은 1자 이상 500자 이하여야 합니다.");
        }
    }

    private void validateRecordDate(LocalDate recordDate) {
        if (recordDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("날짜는 오늘 이전 날짜여야 합니다.");
        }
    }
}
