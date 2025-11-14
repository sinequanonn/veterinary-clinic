package vet.vetclinic.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.dto.request.PetCreateRequest;
import vet.vetclinic.dto.request.PetUpdateRequest;
import vet.vetclinic.dto.response.PetResponse;
import vet.vetclinic.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetResponse> createPet(@RequestBody @Valid PetCreateRequest request) {
        PetResponse response = petService.createPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PetResponse>> findAll() {
        List<PetResponse> responses = petService.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetResponse> findById(@PathVariable Long petId) {
        PetResponse response = petService.findById(petId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetResponse> updatePet(@PathVariable Long petId, @RequestBody @Valid PetUpdateRequest request) {
        PetResponse response = petService.updatePet(petId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }
}
