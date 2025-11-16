package vet.vetclinic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vet.vetclinic.domain.MedicalReport;
import vet.vetclinic.domain.Pet;
import vet.vetclinic.domain.Vet;
import vet.vetclinic.dto.request.MedicalReportCreateRequest;
import vet.vetclinic.dto.request.MedicalReportUpdateRequest;
import vet.vetclinic.dto.response.MedicalReportOfPetResponse;
import vet.vetclinic.dto.response.MedicalReportResponse;
import vet.vetclinic.exception.BusinessException;
import vet.vetclinic.exception.ErrorCode;
import vet.vetclinic.repository.MedicalReportRepository;
import vet.vetclinic.repository.PetRepository;
import vet.vetclinic.repository.VetRepository;

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
    public MedicalReportResponse createMedicalReport(MedicalReportCreateRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PET404));
        Vet vet = vetRepository.findById(request.getVetId())
                .orElseThrow(() -> new BusinessException(ErrorCode.VET404));

        return MedicalReportResponse.from(medicalReportRepository.save(request.toEntity(pet, vet)));
    }

    public MedicalReportResponse findById(Long reportId) {
        return medicalReportRepository.findById(reportId)
                .map(MedicalReportResponse::from)
                .orElseThrow(() -> new BusinessException(ErrorCode.OPN404));
    }

    public List<MedicalReportOfPetResponse> findByPetId(Long petId) {
        return medicalReportRepository.findByPet_PetId(petId).stream()
                .map(MedicalReportOfPetResponse::from)
                .toList();
    }

    @Transactional
    public MedicalReportResponse updateMedicalReport(Long reportId, MedicalReportUpdateRequest request) {
        MedicalReport medicalReport = medicalReportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ErrorCode.OPN404));

        medicalReport.update(
                request.getReportDate(),
                request.getChiefComplaint(),
                request.getAssessment(),
                request.getPlan(),
                request.getPostoperativeCare());

        return MedicalReportResponse.from(medicalReport);
    }

    @Transactional
    public void deleteMedicalReport(Long reportId) {
        if (!medicalReportRepository.existsById(reportId)) {
            throw new BusinessException(ErrorCode.OPN404);
        }
        medicalReportRepository.deleteById(reportId);
    }

}
