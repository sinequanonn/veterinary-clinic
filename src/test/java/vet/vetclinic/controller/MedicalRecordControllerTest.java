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
import vet.vetclinic.domain.MedicalRecord;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.dto.request.MedicalRecordRequest;
import vet.vetclinic.dto.request.VetCreateRequest;
import vet.vetclinic.dto.response.VetResponse;
import vet.vetclinic.service.MedicalRecordService;
import vet.vetclinic.service.PetService;
import vet.vetclinic.service.VetService;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired private MedicalRecordService medicalRecordService;
    private Pet pet;
    private VetResponse vet;

    @BeforeEach
    void setUp() {
        pet = petService.register("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15));

        VetCreateRequest vetRequest = VetCreateRequest.builder().vetName("박진우").build();
        vet = vetService.createVet(vetRequest);
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

    @Test
    void 환자번호로_환자의_진료_기록_목록을_조회한다() throws Exception {
        //given
        medicalRecordService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 7), "식욕부진1", "체온저하1", "급성테스트1", "수액1");
        medicalRecordService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 8), "식욕부진2", "체온저하2", "급성테스트2", "수액2");
        medicalRecordService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 9), "식욕부진3", "체온저하3", "급성테스트3", "수액3");

        //when&then
        mockMvc.perform(get("/api/v1/medical-records/pets/{petId}", pet.getPetId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].recordDate").value("2025-11-07"))
                .andExpect(jsonPath("$[1].recordDate").value("2025-11-08"))
                .andExpect(jsonPath("$[2].recordDate").value("2025-11-09"));
    }

    @Test
    void 진료기록번호로_진료기록을_조회한다() throws Exception {
        //given
        MedicalRecord medicalRecord = medicalRecordService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 7), "식욕부진1", "체온저하1", "급성테스트1", "수액1");

        //when&then
        mockMvc.perform(get("/api/v1/medical-records/{recordId}", medicalRecord.getMedialRecordId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recordId").value(medicalRecord.getMedialRecordId()))
                .andExpect(jsonPath("$.petName").value("뽀삐"))
                .andExpect(jsonPath("$.vetName").value("박진우"))
                .andExpect(jsonPath("$.assessment").value("급성테스트1"));
    }

    @Test
    void 진료기록을_수정한다() throws Exception {
        //given
        MedicalRecord medicalRecord = medicalRecordService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 7), "식욕부진1", "체온저하1", "급성테스트1", "수액1");

        MedicalRecordRequest medicalRecordRequest = new MedicalRecordRequest(
                null, null, LocalDate.of(2025, 11, 8),
                "기침증상",
                "체온정상",
                "급성테스트수정",
                "약처방수정"
        );

        //when&then
        mockMvc.perform(put("/api/v1/medical-records/{recordId}", medicalRecord.getMedialRecordId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(medicalRecordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recordDate").value("2025-11-08"))
                .andExpect(jsonPath("$.subjective").value("기침증상"))
                .andExpect(jsonPath("$.objective").value("체온정상"))
                .andExpect(jsonPath("$.assessment").value("급성테스트수정"))
                .andExpect(jsonPath("$.plan").value("약처방수정"));
    }

    @Test
    void 진료기록을_삭제한다() throws Exception {
        //given
        MedicalRecord medicalRecord = medicalRecordService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 7), "식욕부진1", "체온저하1", "급성테스트1", "수액1");

        //when&then
        mockMvc.perform(delete("/api/v1/medical-records/{recordId}", medicalRecord.getMedialRecordId()))
                .andExpect(status().isNoContent());
    }
}
