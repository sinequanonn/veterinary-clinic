package vet.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.vetclinic.domain.MedicalReport;

public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
}
