package vet.vetclinic.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vet.vetclinic.dto.request.VetCreateRequest;
import vet.vetclinic.dto.response.VetResponse;
import vet.vetclinic.service.VetService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vet")
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @PostMapping
    public ResponseEntity<VetResponse> createVet(
            @RequestBody @Valid VetCreateRequest request) {
        VetResponse response = vetService.createVet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<VetResponse>> findAll() {
        List<VetResponse> responses = vetService.findAll();
        return ResponseEntity.ok(responses);
    }
}
