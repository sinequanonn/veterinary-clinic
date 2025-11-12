package vet.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.MedicalRecordRequest;
import vet.vetclinic.service.PetService;
import vet.vetclinic.service.VetService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MedicalRecordControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired private VetService vetService;
    @Autowired private PetService petService;
    private Pet pet;
    private Vet vet;

    @BeforeEach
    void setUp() {
        pet = petService.register("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15));
        vet = vetService.register("박진우");
    }

    @Test
    void 수의사는_환자의_진료기록을_작성한다() throws Exception {
        //given
        MedicalRecordRequest request = new MedicalRecordRequest(
                pet.getPetId(),
                vet.getVetId(),
                LocalDate.of(2025, 11, 10),
                "식욕 저하",
                "체온 비정상",
                "급성 테스트 작성",
                "수액처치"
        );

        //when&then
        mockMvc.perform(post("/api/v1/medical-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recordId").exists())
                .andExpect(jsonPath("$.petId").value(pet.getPetId()))
                .andExpect(jsonPath("$.petName").value("뽀삐"))
                .andExpect(jsonPath("$.vetId").value(vet.getVetId()))
                .andExpect(jsonPath("$.vetName").value("박진우"))
                .andExpect(jsonPath("$.recordDate").value("2025-11-10"))
                .andExpect(jsonPath("$.subjective").value("식욕 저하"))
                .andExpect(jsonPath("$.objective").value("체온 비정상"))
                .andExpect(jsonPath("$.assessment").value("급성 테스트 작성"))
                .andExpect(jsonPath("$.plan").value("수액처치"));
    }
}
