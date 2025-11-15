package vet.vetclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.dto.request.VetCreateRequest;
import vet.vetclinic.service.VetService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VetService vetService;

    @Test
    void 수의사를_등록한다() throws Exception {
        //given
        VetCreateRequest request = new VetCreateRequest("박진우");

        //when&then
        mockMvc.perform(post("/api/v1/vet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vetId").exists())
                .andExpect(jsonPath("$.vetName").value("박진우"));
    }

    @Test
    void 모든_수의사를_조회한다() throws Exception {
        //given
        vetService.createVet(new VetCreateRequest("박진우"));
        vetService.createVet(new VetCreateRequest("박진웅"));

        //when&then
        mockMvc.perform(get("/api/v1/vet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].vetName").value("박진우"))
                .andExpect(jsonPath("$[1].vetName").value("박진웅"));
    }
}
