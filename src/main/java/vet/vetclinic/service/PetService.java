package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.repository.PetRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public Pet register(String petName, String ownerName, String breed, double weight, LocalDate birthDate) {
        Pet pet = new Pet(petName, ownerName, breed, weight, birthDate);
        return petRepository.save(pet);
    }

    public Pet findById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환자입니다."));
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Transactional
    public Pet update(Long petId, String petName, String ownerName, String breed, double weight, LocalDate birthDate) {
        Pet foundPet = findById(petId);
        foundPet.updatePet(petName, ownerName, breed, weight, birthDate);
        return foundPet;
    }

    @Transactional
    public void delete(Long petId) {
        petRepository.deleteById(petId);
    }
}
