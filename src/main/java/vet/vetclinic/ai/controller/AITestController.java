package vet.vetclinic.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vet.vetclinic.ai.service.AITestService;

@Controller
@RequestMapping("/api/v1/ai")
public class AITestController {
    private final AITestService aiTestService;

    public AITestController(AITestService aiTestService) {
        this.aiTestService = aiTestService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> testChat(@RequestParam String message) {
        String response = aiTestService.testChat(message);
        return ResponseEntity.ok(response);
    }
}
