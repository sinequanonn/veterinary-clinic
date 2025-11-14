package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.dto.request.PetCreateRequest;
import vet.vetclinic.dto.request.PetUpdateRequest;
import vet.vetclinic.dto.response.PetResponse;
import vet.vetclinic.repository.PetRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public PetResponse createPet(PetCreateRequest request) {
        return PetResponse.from(petRepository.save(request.toEntity()));
    }

    public PetResponse findById(Long petId) {
        return petRepository.findById(petId)
                .map(PetResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환자입니다."));
    }

    public List<PetResponse> findAll() {
        return petRepository.findAll().stream()
                .map(PetResponse::from)
                .toList();
    }

    @Transactional
    public PetResponse updatePet(Long petId, PetUpdateRequest request) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환자입니다."));
        pet.updatePet(
                request.getPetName(),
                request.getOwnerName(),
                request.getBreed(),
                request.getWeight(),
                request.getBirthDate()
        );
        return PetResponse.from(pet);
    }

    @Transactional
    public void deletePet(Long petId) {
        if (!petRepository.existsById(petId)) {
            throw new IllegalArgumentException("존재하지 않는 환자입니다.");
        }
        petRepository.deleteById(petId);
    }
}
