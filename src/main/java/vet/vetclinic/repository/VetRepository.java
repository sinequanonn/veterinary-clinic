package vet.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vet.vetclinic.domain.Vet;

public interface VetRepository extends JpaRepository<Vet, Long> {
}
