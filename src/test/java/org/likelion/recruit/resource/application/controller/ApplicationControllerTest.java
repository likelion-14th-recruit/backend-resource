package org.likelion.recruit.resource.application.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;
import org.likelion.recruit.resource.application.service.command.AnswerCommandService;
import org.likelion.recruit.resource.application.service.command.ApplicationCommandService;
import org.likelion.recruit.resource.application.service.query.ApplicationQueryService;
import org.likelion.recruit.resource.application.service.query.AnswerQueryService;
import org.likelion.recruit.resource.application.service.query.QuestionQueryService;
import org.likelion.recruit.resource.interview.service.command.InterviewAvailableCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockitoBean
    private ApplicationCommandService applicationCommandService;

    @MockitoBean
    private ApplicationQueryService applicationQueryService;

    @MockitoBean
    private QuestionQueryService questionQueryService;

    @MockitoBean
    private AnswerCommandService answerCommandService;

    @MockitoBean
    private AnswerQueryService answerQueryService;

    @MockitoBean
    private InterviewAvailableCommandService interviewAvailableCommandService;

    @Test
    @DisplayName("공개 ID로 지원서 답변을 조회하면 200 ok와 응답 반환")
    void getAnswers_Api_Success() throws Exception {
        // given
        String publicId = "app-7c4ec3c9-c31f-4e31-bd48-a4377ea63850";

        // 1. 테스트에 사용할 mock 데이터(AnswerInfo 리스트) 직접 생성
        List<AnswersResult.AnswerInfo> answerInfos = List.of(
                new AnswersResult.AnswerInfo(1L, "안녕하세요"),
                new AnswersResult.AnswerInfo(2L, "Buenos dias!"),
                new AnswersResult.AnswerInfo(3L, "Buenas tardes!"),
                new AnswersResult.AnswerInfo(6L, "Buenas tardes!")
        );

        // 2. 빌더를 사용하여 mockResult 생성
        AnswersResult mockResult = AnswersResult.builder()
                .applicationPublicId(publicId)
                .answers(answerInfos)
                .build();

        // 3. 서비스 모킹
        given(answerQueryService.getAnswers(anyString())).willReturn(mockResult);

        // when, then
        mockMvc.perform(get("/applications/{application-public-id}/answers", publicId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("지원서 답변을 조회하였습니다."))
                .andExpect(jsonPath("$.data.applicationPublicId").value(publicId))
                .andExpect(jsonPath("$.data.answers[0].questionId").value(1))
                .andExpect(jsonPath("$.data.answers[0].content").value("안녕하세요"));
    }
}