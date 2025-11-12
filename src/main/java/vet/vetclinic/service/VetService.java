package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Vet;
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
    public Vet register(String vetName) {
        Vet vet = new Vet(vetName);
        return vetRepository.save(vet);
    }

    public Vet findById(Long vetId) {
        return vetRepository.findById(vetId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수의사입니다."));
    }

    public List<Vet> findAll() {
        return vetRepository.findAll();
    }

    @Transactional
    public void delete(Long vetId) {
        vetRepository.deleteById(vetId);
    }
}
