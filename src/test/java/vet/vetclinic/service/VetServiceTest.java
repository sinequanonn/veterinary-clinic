package vet.vetclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.request.VetCreateRequest;
import vet.vetclinic.dto.response.VetResponse;
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
        VetCreateRequest request = new VetCreateRequest(vetName);
        Vet vet = new Vet(vetName);
        when(vetRepository.save(any(Vet.class))).thenReturn(vet);

        //when
        VetResponse response = vetService.createVet(request);

        //then
        assertThat(response.getVetName()).isEqualTo(vetName);
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
        List<VetResponse> foundVets = vetService.findAll();

        //then
        assertThat(foundVets).hasSize(2);
        assertThat(foundVets).extracting("vetName")
                .containsExactly("박진우", "박지운");
        verify(vetRepository, times(1)).findAll();
    }
}
