package vet.vetclinic.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.repository.VetRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VetServiceTest {
    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetService vetService;

    @Test
    void 수의사는_회원가입을_한다() {
        //given
        String vetName = "박진우";
        Vet vet = new Vet(vetName);
        when(vetRepository.save(any(Vet.class))).thenReturn(vet);

        //when
        Vet registered = vetService.register(vetName);

        //then
        assertThat(registered.getVetName()).isEqualTo(vetName);
        verify(vetRepository, times(1)).save(any(Vet.class));
    }

    @Test
    void 모든_수의사의_목록을_조회한다() {
        //given
        List<Vet> vets = List.of(
                new Vet("박진우"),
                new Vet("박지운")
        );
        when(vetRepository.findAll()).thenReturn(vets);

        //when
        List<Vet> foundVets = vetService.findAll();

        //then
        assertThat(foundVets).hasSize(2);
        assertThat(foundVets).extracting("vetName")
                .containsExactly("박진우", "박지운");
        verify(vetRepository, times(1)).findAll();
    }
}
