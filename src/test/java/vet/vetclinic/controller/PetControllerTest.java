package vet.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.dto.request.PetCreateRequest;
import vet.vetclinic.dto.request.PetUpdateRequest;
import vet.vetclinic.dto.response.PetResponse;
import vet.vetclinic.service.PetService;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PetControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PetService petService;

    @Test
    void 환자를_등록한다() throws Exception {
        //given
        PetCreateRequest request = PetCreateRequest.builder()
                .petName("뽀삐")
                .ownerName("박진우")
                .breed("말티즈")
                .weight(3.5)
                .birthDate(LocalDate.of(2025, 11, 8))
                .build();

        //when&then
        mockMvc.perform(post("/api/v1/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petId").exists())
                .andExpect(jsonPath("$.petName").value("뽀삐"))
                .andExpect(jsonPath("$.ownerName").value("박진우"))
                .andExpect(jsonPath("$.breed").value("말티즈"))
                .andExpect(jsonPath("$.weight").value(3.5))
                .andExpect(jsonPath("$.birthDate").value("2025-11-08"));
    }

    @Test
    void 모든_환자를_조회한다() throws Exception {
        // given
        petService.createPet(new PetCreateRequest("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15)));
        petService.createPet(new PetCreateRequest("나비", "박진웅", "웰시코기", 4.2, LocalDate.of(2019, 3, 20)));

        //when&then
        mockMvc.perform(get("/api/v1/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].petName").value("뽀삐"))
                .andExpect(jsonPath("$[1].petName").value("나비"));

    }

    @Test
    void 환자번호로_하나의_환자를_상세_조회한다() throws Exception {
        //given
        PetResponse response = petService.createPet(new PetCreateRequest("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15)));

        //when&then
        mockMvc.perform(get("/api/v1/pet/{petId}", response.getPetId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petId").value(response.getPetId()))
                .andExpect(jsonPath("$.petName").value("뽀삐"));
    }

    @Test
    void 환자정보를_수정한다() throws Exception {
        // given
        PetResponse response = petService.createPet(new PetCreateRequest("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15)));;
        PetUpdateRequest request = new PetUpdateRequest(
                "초코",
                "박진우",
                "말티즈",
                4.1,
                LocalDate.of(2020, 5, 15)
        );

        //when&then
        mockMvc.perform(put("/api/v1/pet/{petId}", response.getPetId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName").value("초코"))
                .andExpect(jsonPath("$.weight").value(4.1));
    }

    @Test
    void 환자정보를_삭제한다() throws Exception {
        // given
        PetResponse response = petService.createPet(new PetCreateRequest("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15)));;

        // when & then
        mockMvc.perform(delete("/api/v1/pet/{petId}", response.getPetId()))
                .andExpect(status().isNoContent());
    }
}
