package vet.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.vetclinic.domain.MedicalReport;

import java.util.List;

public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
    List<MedicalReport> findByPet_PetId(Long petPetId);
}
