package org.likelion.recruit.resource.recommend.version.engine.v3.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.recommend.service.OpenAITestService;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;
import org.likelion.recruit.resource.recommend.version.engine.v3.service.OpenAIWeightTuningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open-ai")
@RequiredArgsConstructor
public class OpenAIController {

    private final OpenAITestService openAITestService;
    private final OpenAIWeightTuningService openAIWeightTuningService;

    /**
     * OpenAI 테스트
     */
    @GetMapping("/test")
    public ResponseEntity<String> testOpenAI() {
        return ResponseEntity.ok(openAITestService.testOpenAI());
    }

    /**
     * OpenAI 가중치 보정 제안
     */
    @PostMapping("/recommend")
    public ResponseEntity<String> recommendNextWeight(@RequestBody WeightTuningRequest req) {
        return ResponseEntity.ok(openAIWeightTuningService.recommendNextWeight(req));
    }
}
