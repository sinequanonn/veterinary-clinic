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
import vet.vetclinic.domain.MedicalReport;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.MedicalReportRequest;
import vet.vetclinic.service.MedicalReportService;
import vet.vetclinic.service.PetService;
import vet.vetclinic.service.VetService;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MedicalReportControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private VetService vetService;
    @Autowired private PetService petService;
    @Autowired private MedicalReportService medicalReportService;

    private Pet pet;
    private Vet vet;

    @BeforeEach
    void setUp() {
        pet = petService.register("뽀삐", "박진우", "말티즈", 3.5, LocalDate.of(2020, 5, 15));
        vet = vetService.register("박진우");
    }

    @Test
    void 진료소견서를_등록한다() throws Exception {
        //given
        MedicalReportRequest request = new MedicalReportRequest(
                pet.getPetId(),
                vet.getVetId(),
                LocalDate.of(2025, 11, 10),
                "식욕부진, 구토",
                "식도염",
                "수액처치 및 약물 투여",
                "3일간 금식, 물만 제공"
        );

        //when&then
        mockMvc.perform(post("/api/v1/medical-reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reportId").exists())
                .andExpect(jsonPath("$.petId").value(pet.getPetId()))
                .andExpect(jsonPath("$.petName").value("뽀삐"))
                .andExpect(jsonPath("$.vetId").value(vet.getVetId()))
                .andExpect(jsonPath("$.vetName").value("박진우"))
                .andExpect(jsonPath("$.reportDate").value("2025-11-10"))
                .andExpect(jsonPath("$.chiefComplaint").value("식욕부진, 구토"))
                .andExpect(jsonPath("$.assessment").value("식도염"))
                .andExpect(jsonPath("$.plan").value("수액처치 및 약물 투여"))
                .andExpect(jsonPath("$.postoperativeCare").value("3일간 금식, 물만 제공"));
    }

    @Test
    void 환자의_진료소견서_목록을_조회한다() throws Exception {
        //given
        medicalReportService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 9), "식욕부진, 구토", "식도염", "수액처치 및 약물 투여", "3일간 금식, 물만 제공");
        medicalReportService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 10), "식욕부진, 구토", "식도염", "수액처치 및 약물 투여", "3일간 금식, 물만 제공");
        medicalReportService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 11), "식욕부진, 구토", "식도염", "수액처치 및 약물 투여", "3일간 금식, 물만 제공");

        //when&then
        mockMvc.perform(get("/api/v1/medical-reports/pets/{petId}", pet.getPetId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].reportDate").value("2025-11-09"))
                .andExpect(jsonPath("$[1].reportDate").value("2025-11-10"))
                .andExpect(jsonPath("$[2].reportDate").value("2025-11-11"));
    }

    @Test
    void 진료소견서번호로_하나의_진료소견서를_조회한다() throws Exception {
        //given
        MedicalReport medicalReport = medicalReportService.create(pet.getPetId(), vet.getVetId(), LocalDate.of(2025, 11, 9), "식욕부진, 구토", "식도염", "수액처치 및 약물 투여", "3일간 금식, 물만 제공");

        // when & then
        mockMvc.perform(get("/api/v1/medical-reports/{reportId}", medicalReport.getMedicalReportId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reportId").value(medicalReport.getMedicalReportId()))
                .andExpect(jsonPath("$.petName").value("뽀삐"))
                .andExpect(jsonPath("$.vetName").value("박진우"))
                .andExpect(jsonPath("$.assessment").value("식도염"));
    }
}
