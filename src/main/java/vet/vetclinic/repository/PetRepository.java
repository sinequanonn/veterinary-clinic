package vet.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.vetclinic.domain.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
