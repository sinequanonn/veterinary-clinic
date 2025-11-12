package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.MedicalReport;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.repository.MedicalReportRepository;
import vet.vetclinic.repository.PetRepository;
import vet.vetclinic.repository.VetRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MedicalReportService {
    private final MedicalReportRepository medicalReportRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    public MedicalReportService(MedicalReportRepository medicalReportRepository, PetRepository petRepository, VetRepository vetRepository) {
        this.medicalReportRepository = medicalReportRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
    }

    @Transactional
    public MedicalReport create(Long petId, Long vetId, LocalDate reportDate,
                                String chiefComplaint, String assessment, String plan, String postoperativeCare) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 환자입니다."));
        Vet vet = vetRepository.findById(vetId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수의사입니다."));
        MedicalReport medicalReport = new MedicalReport(pet, vet, reportDate, chiefComplaint, assessment, plan, postoperativeCare);
        return medicalReportRepository.save(medicalReport);
    }

    public MedicalReport findById(Long reportId) {
        return medicalReportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 진료 소견서입니다."));
    }

    public List<MedicalReport> findByPetId(Long petId) {
        return medicalReportRepository.findByPet_PetId(petId);
    }

    @Transactional
    public void update(Long reportId, LocalDate reportDate,
                                String chiefComplaint, String assessment, String plan, String postoperativeCare) {
        MedicalReport foundMedicalReport = findById(reportId);
        foundMedicalReport.update(reportDate, chiefComplaint, assessment, plan, postoperativeCare);
    }

    @Transactional
    public void delete(Long reportId) {
        medicalReportRepository.deleteById(reportId);
    }

}
