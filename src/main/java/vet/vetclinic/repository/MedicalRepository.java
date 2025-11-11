package vet.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.vetclinic.domain.MedicalRecord;

public interface MedicalRepository extends JpaRepository<MedicalRecord, Long> {
}
