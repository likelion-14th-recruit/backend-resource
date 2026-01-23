package org.likelion.recruit.resource.recommend.version.engine.v3.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.recommend.service.OpenAITestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-ai")
@RequiredArgsConstructor
public class OpenAIController {

    private final OpenAITestService openAITestService;

    @GetMapping("/test")
    public ResponseEntity<String> testOpenAI() {
        return ResponseEntity.ok(openAITestService.testOpenAI());
    }
}
