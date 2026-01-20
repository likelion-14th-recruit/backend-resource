package org.likelion.recruit.resource.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.command.AnswerCommand;
import org.likelion.recruit.resource.application.dto.command.ApplicationCreateCommand;
import org.likelion.recruit.resource.application.dto.request.AnswersRequest;
import org.likelion.recruit.resource.application.dto.request.ApplicationCreateRequest;
import org.likelion.recruit.resource.application.dto.response.PublicIdResponse;
import org.likelion.recruit.resource.application.dto.response.QuestionsResponse;
import org.likelion.recruit.resource.application.dto.result.PublicIdResult;
import org.likelion.recruit.resource.application.dto.result.QuestionsResult;
import org.likelion.recruit.resource.application.service.command.AnswerCommandService;
import org.likelion.recruit.resource.application.service.command.ApplicationCommandService;
import org.likelion.recruit.resource.application.service.query.ApplicationQueryService;
import org.likelion.recruit.resource.application.service.query.QuestionQueryService;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationCommandService applicationCommandService;
    private final ApplicationQueryService applicationQueryService;
    private final QuestionQueryService questionQueryService;
    private final AnswerCommandService answerCommandService;

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
    @GetMapping("/questions/{application-public-id}")
    public ResponseEntity<ApiResponse<QuestionsResponse>> getQuestions(@PathVariable("application-public-id") String publicId){
        QuestionsResult results = questionQueryService.getQuestions(publicId);

        return ResponseEntity.ok(ApiResponse.success("지원서 질문을 조회하였습니다.", QuestionsResponse.from(results)));
    }

    /**
     * 지원서 답변 저장하기
     */
    @PostMapping("/answers")
    public ResponseEntity<ApiResponse<AnswersRequest>> createAnswers(@RequestBody AnswersRequest req){
        answerCommandService.createAnswers(req.getApplicationPublicId(), req.getAnswers());

        return ResponseEntity.ok(ApiResponse.success("지원서 답변을 저장하였습니다."));
    }
}
