package vet.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.dto.request.PetRequest;
import vet.vetclinic.dto.response.PetResponse;
import vet.vetclinic.service.PetService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponse> register(@RequestBody PetRequest request) {
        Pet pet = petService.register(
                request.getPetName(),
                request.getOwnerName(),
                request.getBreed(),
                request.getWeight(),
                request.getBirthDate()
        );
        PetResponse response = PetResponse.from(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> findAll() {
        List<PetResponse> responses = petService.findAll().stream()
                .map(PetResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> findById(@PathVariable Long petId) {
        Pet pet = petService.findById(petId);
        PetResponse response = PetResponse.from(pet);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetResponse> update(@PathVariable Long petId, @RequestBody PetRequest request) {
        Pet pet = petService.update(
                petId,
                request.getPetName(),
                request.getOwnerName(),
                request.getBreed(),
                request.getWeight(),
                request.getBirthDate());
        PetResponse response = PetResponse.from(pet);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> delete(@PathVariable Long petId) {
        petService.delete(petId);
        return ResponseEntity.noContent().build();
    }
}
