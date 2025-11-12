package vet.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.dto.PetRequest;

import java.time.LocalDate;

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
}
