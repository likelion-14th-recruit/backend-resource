package org.likelion.recruit.resource.interview.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.interview.dto.response.InterviewTimesResponse;
import org.likelion.recruit.resource.interview.dto.result.InterviewTimesResult;
import org.likelion.recruit.resource.interview.service.query.InterviewTimeQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interivew-times")
@RequiredArgsConstructor
public class InterviewTimeController {

    private final InterviewTimeQueryService interviewTimeQueryService;

    /**
     * 인터뷰 시간 전체 조회하기
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<InterviewTimesResponse>>> getAll(){
        List<InterviewTimesResult> results = interviewTimeQueryService.findAll();

        List<InterviewTimesResponse> responses = results.stream()
                .map(InterviewTimesResponse::from)
                .toList();

        return ResponseEntity.ok(ApiResponse.success("인터뷰 시간 전체 조회에 성공하였습니다.", responses));
    }
}
