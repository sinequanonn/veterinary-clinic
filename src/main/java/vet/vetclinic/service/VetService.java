package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.request.VetCreateRequest;
import vet.vetclinic.dto.response.VetResponse;
import vet.vetclinic.repository.VetRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VetService {
    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Transactional
    public VetResponse createVet(VetCreateRequest request) {
        return VetResponse.from(vetRepository.save(request.toEntity()));
    }

    public VetResponse findById(Long vetId) {
        return vetRepository.findById(vetId)
                .map(VetResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수의사입니다."));
    }

    public List<VetResponse> findAll() {
        return vetRepository.findAll().stream()
                .map(VetResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long vetId) {
        vetRepository.deleteById(vetId);
    }
}
