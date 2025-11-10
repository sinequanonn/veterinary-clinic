package vet.vetclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class MedicalReport {
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

    public MedicalReport(Pet pet, Vet vet, LocalDate reportDate, String chiefComplaint, String assessment, String plan, String postoperativeCare) {
        this.pet = pet;
        this.vet = vet;
        this.reportDate = reportDate;
        this.chiefComplaint = chiefComplaint;
        this.assessment = assessment;
        this.plan = plan;
        this.postoperativeCare = postoperativeCare;
    }
}
