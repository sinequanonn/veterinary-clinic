package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class MedicalReport {
    public static final int MAX_CHIEF_COMPLAINT_LENGTH = 300;
    public static final int MAX_ASSESSMENT_LENGTH = 300;
    public static final int MAX_PLAN_LENGTH = 500;
    public static final int MAX_POSTOPERATIVE_CARE_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalReportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)

    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id", nullable = false)
    private Vet vet;

    @Column(nullable = false)
    private LocalDate reportDate;

    @Column(nullable = false)
    private String chiefComplaint;

    @Column(nullable = false)
    private String assessment;

    @Column(nullable = false)
    private String plan;

    @Column(nullable = false)
    private String postoperativeCare;

    protected MedicalReport() {
    }

    public MedicalReport(Pet pet, Vet vet, LocalDate reportDate, String chiefComplaint, String assessment, String plan, String postoperativeCare) {
        validateReportDate(reportDate);
        validateChiefComplaint(chiefComplaint);
        validateAssessment(assessment);
        validatePlan(plan);
        validatePostoperativeCare(postoperativeCare);

        this.pet = pet;
        this.vet = vet;
        this.reportDate = reportDate;
        this.chiefComplaint = chiefComplaint;
        this.assessment = assessment;
        this.plan = plan;
        this.postoperativeCare = postoperativeCare;
    }

    private void validatePostoperativeCare(String postoperativeCare) {
        if (postoperativeCare == null || postoperativeCare.isBlank()) {
            throw new IllegalArgumentException("추후 관리는 1자 이상 500자 이하여야 합니다.");
        }

        if (postoperativeCare.length() > MAX_POSTOPERATIVE_CARE_LENGTH) {
            throw new IllegalArgumentException("추후 관리는 1자 이상 500자 이하여야 합니다.");
        }
    }

    private void validatePlan(String plan) {
        if (plan == null || plan.isBlank()) {
            throw new IllegalArgumentException("치료 계획은 1자 이상 500자 이하여야 합니다.");
        }

        if (plan.length() > MAX_PLAN_LENGTH) {
            throw new IllegalArgumentException("치료 계획은 1자 이상 500자 이하여야 합니다.");
        }
    }

    private void validateAssessment(String assessment) {
        if (assessment == null || assessment.isBlank()) {
            throw new IllegalArgumentException("확정 진단은 1자 이상 300자 이하여야 합니다.");
        }

        if (assessment.length() > MAX_ASSESSMENT_LENGTH) {
            throw new IllegalArgumentException("확정 진단은 1자 이상 300자 이하여야 합니다.");
        }
    }

    private void validateChiefComplaint(String chiefComplaint) {
        if (chiefComplaint == null || chiefComplaint.isBlank()) {
            throw new IllegalArgumentException("주요 증상은 1자 이상 300자 이하여야 합니다.");
        }

        if (chiefComplaint.length() > MAX_CHIEF_COMPLAINT_LENGTH) {
            throw new IllegalArgumentException("주요 증상은 1자 이상 300자 이하여야 합니다.");
        }
    }

    private void validateReportDate(LocalDate reportDate) {
        if (reportDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("날짜는 오늘 이전 날짜여야 합니다.");
        }
    }

}
