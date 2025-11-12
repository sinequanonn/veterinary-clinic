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
import vet.vetclinic.dto.PetRequest;
import vet.vetclinic.service.PetService;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        PetRequest request = new PetRequest("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2025, 11, 8));

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
        petService.register("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15));
        petService.register("나비", "박진웅", "웰시코기", 4.2, LocalDate.of(2019, 3, 20));

        //when&then
        mockMvc.perform(get("/api/v1/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].petName").value("뽀삐"))
                .andExpect(jsonPath("$[1].petName").value("나비"));

    }
}
