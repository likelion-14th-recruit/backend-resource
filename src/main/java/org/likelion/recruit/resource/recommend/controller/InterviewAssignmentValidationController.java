package org.likelion.recruit.resource.recommend.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.recommend.dto.request.InterviewAssignmentValidationRequest;
import org.likelion.recruit.resource.recommend.dto.response.InterviewAssignmentResponse;
import org.likelion.recruit.resource.recommend.dto.response.ValidationResponse;
import org.likelion.recruit.resource.recommend.dto.result.AvailabilityViolation;
import org.likelion.recruit.resource.recommend.service.InterviewAssignmentValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interview-schedule/recommend")
public class InterviewAssignmentValidationController {

    private final InterviewAssignmentValidationService validationService;

    /**
     * V2.1 검증
     */
    @PostMapping("/v2.1/validate")
    public ResponseEntity<ApiResponse<List<AvailabilityViolation>>> validate(@RequestBody InterviewAssignmentValidationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("검증 성공",validationService.validate(request)));
    }
}
