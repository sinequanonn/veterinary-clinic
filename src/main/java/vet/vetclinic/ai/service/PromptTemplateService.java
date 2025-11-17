package vet.vetclinic.ai.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import vet.vetclinic.ai.domain.MedicalContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class PromptTemplateService {
    private static final String SYSTEM_PROMPT_PATH = "prompts/medical-report-system.txt";
    private static final String USER_PROMPT_PATH = "prompts/medical-report-user.txt";

    public String getSystemPrompt() {
        return loadPromptFromResource(SYSTEM_PROMPT_PATH);
    }

    public String getUserPrompt(MedicalContext context) {
        String template = loadPromptFromResource(USER_PROMPT_PATH);

        return template.replace("{context}", context.toText());
    }

    private String loadPromptFromResource(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            byte[] bytes = resource.getInputStream().readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("프롬프트 파일 로드를 실패했습니다.");
        }
    }
}
