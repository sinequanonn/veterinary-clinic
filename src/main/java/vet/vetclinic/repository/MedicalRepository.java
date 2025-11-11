package vet.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.vetclinic.domain.MedicalRecord;

import java.util.List;

public interface MedicalRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPet_PetId(Long petPetId);
}
