package org.likelion.recruit.resource.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.dto.request.InterviewScheduleRequest;
import org.likelion.recruit.resource.interview.dto.response.InterviewScheduleResponse;
import org.likelion.recruit.resource.interview.dto.result.InterviewScheduleResult;
import org.likelion.recruit.resource.interview.service.command.InterviewScheduleCommandService;
import org.likelion.recruit.resource.interview.service.query.InterviewScheduleQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications/{application-public-id}/interview-schedule/select")
public class InterviewScheduleController {

    private final InterviewScheduleCommandService interviewScheduleCommandService;
    private final InterviewScheduleQueryService interviewScheduleQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InterviewScheduleResponse>> upsertInterviewSchedule(
            @PathVariable("application-public-id") String applicationPublicId,
            @Valid @RequestBody InterviewScheduleRequest request) {
        interviewScheduleCommandService.upsertInterviewSchedule(InterviewScheduleCommand.of(applicationPublicId, request));
        InterviewScheduleResult result = interviewScheduleQueryService.getInterviewSchedule(applicationPublicId);
        return ResponseEntity.ok(ApiResponse.success("면접 관련 정보가 성공적으로 저장되었습니다.", InterviewScheduleResponse.from(result)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<InterviewScheduleResponse>> getInterviewSchedule(
            @PathVariable("application-public-id") String applicationPublicId
    ){
        InterviewScheduleResult result = interviewScheduleQueryService.getInterviewSchedule(applicationPublicId);

        return ResponseEntity.ok(ApiResponse.success("지원자의 면접 일정 조회에 성공하셨습니다.",InterviewScheduleResponse.from(result)));
    }
}
