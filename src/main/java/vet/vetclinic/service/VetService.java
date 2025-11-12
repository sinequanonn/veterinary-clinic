package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.repository.VetRepository;

import java.util.List;

@Service
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

    public List<Vet> findAll() {
        return vetRepository.findAll();
    }
}
