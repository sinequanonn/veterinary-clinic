package vet.vetclinic.ai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;
import vet.vetclinic.ai.domain.MedicalContext;
import vet.vetclinic.ai.dto.GeneratedMedicalReportResponse;

@Slf4j
@Service
public class MedicalReportAIService {
    private final ChatClient chatClient;
    private final MedicalContextService medicalContextService;
    private final PromptTemplateService promptTemplateService;

    public MedicalReportAIService(ChatClient chatClient, MedicalContextService medicalContextService, PromptTemplateService promptTemplateService) {
        this.chatClient = chatClient;
        this.medicalContextService = medicalContextService;
        this.promptTemplateService = promptTemplateService;
    }

    public GeneratedMedicalReportResponse generatedMedicalReport(Long recordId) {
        MedicalContext context = medicalContextService.makeContext(recordId);

        BeanOutputConverter<GeneratedMedicalReportResponse> outputConverter = new BeanOutputConverter<>(GeneratedMedicalReportResponse.class);

        String systemPrompt = promptTemplateService.getSystemPrompt();
        String userPrompt = promptTemplateService.getUserPrompt(context);

        String finalPrompt = userPrompt + "\n" + outputConverter.getFormat();

        return chatClient.prompt()
                .system(systemPrompt)
                .user(finalPrompt)
                .call()
                .entity(GeneratedMedicalReportResponse.class);
    }
}
