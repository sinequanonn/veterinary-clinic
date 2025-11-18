package vet.vetclinic.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AITestService {
    private final ChatClient chatClient;

    public AITestService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String testChat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
