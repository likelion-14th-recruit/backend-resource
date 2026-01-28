package org.likelion.recruit.resource.interview.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.dto.request.InterviewScheduleRequest;
import org.likelion.recruit.resource.interview.service.command.InterviewScheduleCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class InterviewScheduleController {

    private final InterviewScheduleCommandService interviewScheduleCommandService;

    @PostMapping("/{applicationPublicId}/interview-schedule/select")
    public ResponseEntity<ApiResponse<Void>> upsertInterviewSchedule(
            @PathVariable String applicationPublicId,
            @Valid @RequestBody InterviewScheduleRequest request) {
        interviewScheduleCommandService.upsertInterviewSchedule(InterviewScheduleCommand.of(applicationPublicId, request));
        return ResponseEntity.ok(ApiResponse.success("면접 관련 정보가 성공적으로 저장되었습니다."));
    }
}
