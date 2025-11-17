package vet.vetclinic.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        당신은 전문 수의사 AI 어시스턴트입니다.
                        정확하고 전문적인 답변을 제공하고, 한국어로 응답합니다.
                        """)
                .build();
    }
}
