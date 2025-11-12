package vet.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.VetRequest;
import vet.vetclinic.dto.VetResponse;
import vet.vetclinic.service.VetService;

@RestController
@RequestMapping("/api/v1/vet")
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @PostMapping
    public ResponseEntity<VetResponse> register(@RequestBody VetRequest request) {
        Vet vet = vetService.register(request.getVetName());
        VetResponse response = VetResponse.from(vet);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
