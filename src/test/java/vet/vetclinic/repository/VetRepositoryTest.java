package vet.vetclinic.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vet.vetclinic.domain.Vet;

import java.util.List;

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

    @Test
    void 모든_수의사를_조회한다() {
        //given
        vetRepository.save(new Vet("박진우"));
        vetRepository.save(new Vet("박진운"));
        vetRepository.save(new Vet("김철수"));

        //when
        List<Vet> vets = vetRepository.findAll();

        //then
        assertThat(vets).hasSize(3);
        assertThat(vets)
                .extracting("vetName")
                .containsExactly("박진우", "박진운", "김철수");
    }
}
