package org.likelion.recruit.resource.recommend.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.recommend.dto.response.InterviewAssignmentResponse;
import org.likelion.recruit.resource.recommend.dto.result.AssignmentResult;
import org.likelion.recruit.resource.recommend.service.InterviewAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interview-schedule/recommend")
public class InterviewAssignmentController {

    private final InterviewAssignmentService interviewAssignmentService;

    /**
     * 면접 배정 실행 V1
     */
    @GetMapping("/v1")
    public ResponseEntity<ApiResponse<InterviewAssignmentResponse>> assignInterviewV1() {

        AssignmentResult result =
                interviewAssignmentService.assignInterviewV1();

        return ResponseEntity.ok(ApiResponse.success("V1 모델 생성",InterviewAssignmentResponse.from(result)));
    }

    /**
     * 면접 배정 실행 V2
     */
    @GetMapping("/v2")
    public ResponseEntity<ApiResponse<InterviewAssignmentResponse>> assignInterviewV2() {

        AssignmentResult result =
                interviewAssignmentService.assignInterviewV2();

        return ResponseEntity.ok(ApiResponse.success("V1 모델 생성",InterviewAssignmentResponse.from(result)));
    }

    /**
     * 면접 배정 실행 V2.1
     */
    @GetMapping("/v2.1")
    public ResponseEntity<ApiResponse<InterviewAssignmentResponse>> assignInterviewV21() {

        AssignmentResult result =
                interviewAssignmentService.assignInterviewV21();

        return ResponseEntity.ok(ApiResponse.success("V1 모델 생성",InterviewAssignmentResponse.from(result)));
    }
}
