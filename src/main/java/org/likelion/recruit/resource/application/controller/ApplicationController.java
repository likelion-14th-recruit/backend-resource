package org.likelion.recruit.resource.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.command.ApplicationCreateCommand;
import org.likelion.recruit.resource.application.dto.command.ApplicationSearchCommand;
import org.likelion.recruit.resource.application.dto.command.ApplicationUpdateCommand;
import org.likelion.recruit.resource.application.dto.request.AnswersRequest;
import org.likelion.recruit.resource.application.dto.request.ApplicationCreateRequest;
import org.likelion.recruit.resource.application.dto.request.ApplicationSearchRequest;
import org.likelion.recruit.resource.application.dto.response.*;
import org.likelion.recruit.resource.application.dto.result.*;
import org.likelion.recruit.resource.application.service.command.AnswerCommandService;
import org.likelion.recruit.resource.application.service.command.ApplicationCommandService;
import org.likelion.recruit.resource.application.service.query.AnswerQueryService;
import org.likelion.recruit.resource.application.service.query.ApplicationQueryService;
import org.likelion.recruit.resource.application.service.query.QuestionQueryService;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.common.dto.response.PageResponse;
import org.likelion.recruit.resource.interview.dto.request.InterviewAvailableRequest;
import org.likelion.recruit.resource.interview.dto.response.InterviewAvailableResponse;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;
import org.likelion.recruit.resource.interview.service.command.InterviewAvailableCommandService;
import org.likelion.recruit.resource.interview.service.query.InterviewAvailableQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationCommandService applicationCommandService;
    private final ApplicationQueryService applicationQueryService;
    private final QuestionQueryService questionQueryService;
    private final AnswerCommandService answerCommandService;
    private final AnswerQueryService answerQueryService;
    private final InterviewAvailableCommandService interviewAvailableCommandService;
    private final InterviewAvailableQueryService interviewAvailableQueryService;
    /**
     * 지원서 인적사항 생성하기
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PublicIdResponse>> createApplication(@Valid @RequestBody ApplicationCreateRequest request){
        String publicId = applicationCommandService.createApplication(ApplicationCreateCommand.from(request));
        PublicIdResult result = applicationQueryService.getPublicId(publicId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("지원서 인적사항이 생성되었습니다.", PublicIdResponse.from(result)));
    }

    /**
     * 지원서 질문 조회하기
     */
    @GetMapping("/{application-public-id}/questions")
    public ResponseEntity<ApiResponse<QuestionsResponse>> getQuestions(@PathVariable("application-public-id") String publicId){
        QuestionsResult results = questionQueryService.getQuestions(publicId);

        return ResponseEntity.ok(ApiResponse.success("지원서 질문을 조회하였습니다.", QuestionsResponse.from(results)));
    }

    /**
     * 지원서 답변 저장하기
     */
    @PostMapping("/{application-public-id}/answers")
    public ResponseEntity<ApiResponse<AnswersRequest>> createAnswers(@PathVariable("application-public-id") String publicId,
                                                                     @RequestBody AnswersRequest req){
        answerCommandService.createAnswers(publicId, req.getAnswers());

        return ResponseEntity.ok(ApiResponse.success("지원서 답변을 저장하였습니다."));
    }

    /**
     * 지원서 답변 조회하기
     */
    @GetMapping("/{application-public-id}/answers")
    public ResponseEntity<ApiResponse<AnswersResponse>> getAnswers(
            @PathVariable("application-public-id") String publicId) {
        AnswersResult result = answerQueryService.getAnswers(publicId);
        return ResponseEntity.ok(ApiResponse.success(
                "지원서 답변을 조회하였습니다.",
                AnswersResponse.from(result)));
    }

    /**
     * 면접 가능 시간 생성하기
     */
    @PostMapping("/{application-public-id}/interview-available")
    public ResponseEntity<ApiResponse<Void>> createInterviewAvailable(@PathVariable("application-public-id") String publicId,
                                                                      @RequestBody InterviewAvailableRequest request){
        interviewAvailableCommandService.createInterviewAvailable(publicId, request.getInterviewTimeIds());

        return ResponseEntity.ok(ApiResponse.success("면접 가능 시간을 저장하였습니다."));
    }

    /**
     * 면접 가능 시간 조회하기
     */
    @GetMapping("/{application-public-id}/interview-available")
    public ResponseEntity<ApiResponse<InterviewAvailableResponse>> getInterviewAvailable(@PathVariable("application-public-id") String publicId) {
        InterviewAvailableResult result = interviewAvailableQueryService.getInterviewAvailable(publicId);
        return ResponseEntity.ok(ApiResponse.success(
                "선택한 면접 가능 시간 조회가 완료되었습니다.",
                InterviewAvailableResponse.from(result)));
    }

    /**
     * 지원서 인적사항 조회하기
     */
    @GetMapping("/{application-public-id}")
    public ResponseEntity<ApiResponse<ApplicationDetailResponse>> getApplicationDetail(
            @PathVariable("application-public-id") String publicId,
            @RequestParam("password-length") Integer passwordLength){
        ApplicationDetailResult result = applicationQueryService.getApplicationDetail(publicId, passwordLength);
        return ResponseEntity.ok(ApiResponse.success("지원서 인적사항을 조회하였습니다.",
                ApplicationDetailResponse.from(result)));
    }

    /**
     * 지원서 인적사항 수정하기
     */
    @PatchMapping("/{application-public-id}")
    public ResponseEntity<ApiResponse<Void>> updateApplication(
            @PathVariable("application-public-id") String publicId,
            @RequestBody Map<String, Object> body){

        applicationCommandService.updateApplication(publicId, ApplicationUpdateCommand.from(body));
        return ResponseEntity.ok(ApiResponse.success("지원서 인적사항을 수정하였습니다."));
    }

    /**
     * 지원서 검색 조회하기
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ApplicationSearchResponse>>> searchApplications(ApplicationSearchRequest req,  Pageable pageable){
        Page<ApplicationSearchResult> results = applicationQueryService.searchApplications(ApplicationSearchCommand.from(req), pageable);
        Page<ApplicationSearchResponse> resultsResponse =  results.map(ApplicationSearchResponse::from);

        return ResponseEntity.ok(ApiResponse.success("지원서 검색에 성공하였습니다.", PageResponse.from(resultsResponse)));
    }
}
