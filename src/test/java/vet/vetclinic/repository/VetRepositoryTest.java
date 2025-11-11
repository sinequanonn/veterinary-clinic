package vet.vetclinic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.Vet;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VetRepositoryTest {
    @Autowired
    private VetRepository vetRepository;

    @Test
    void 한_명의_수의사를_저장한다() {
        //given
        Vet vet = new Vet("박진우");

        //when
        Vet savedVet = vetRepository.save(vet);

        //then
        assertThat(savedVet.getVetId()).isNotNull();
        assertThat(savedVet.getVetName()).isEqualTo("박진우");
    }
}
