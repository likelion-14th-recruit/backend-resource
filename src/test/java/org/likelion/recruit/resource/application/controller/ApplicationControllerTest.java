package org.likelion.recruit.resource.application.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;
import org.likelion.recruit.resource.interview.service.command.InterviewAvailableCommandService;
import org.likelion.recruit.resource.interview.service.query.InterviewAvailableQueryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockitoBean
    private InterviewAvailableQueryService interviewAvailableQueryService;

    @Test
    @DisplayName("공개 ID로 지원서 답변을 조회하면 200 ok와 응답 반환")
    void getAnswers_Api_Success() throws Exception {
        String publicId = "app-7c4ec3c9-c31f-4e31-bd48-a4377ea63850";
        AnswersResult mockResult = AnswersResult.of(
                publicId,
                List.of(new AnswersResult.AnswerInfo(1L, "안녕하세요"))
        );

        given(answerQueryService.getAnswers(anyString())).willReturn(mockResult);

        mockMvc.perform(get("/applications/{application-public-id}/answers", publicId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.answers[0].content").value("안녕하세요"));
    }

    @Test
    @DisplayName("지원서 최종 제출을 하면 200 OK와 성공 메시지를 반환")
    void submitApplication_Api_Success() throws Exception {
        // given
        String publicId = "app-7c4ec3c9-c31f-4e31-bd48-a4377ea63850";

        // 리턴값이 void인 서비스 메서드 모킹
        willDoNothing().given(applicationCommandService).submitApplication(publicId);

        // when & then
        mockMvc.perform(post("/applications/{application-public-id}/submit", publicId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("지원서가 성공적으로 제출되었습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    @DisplayName("지원서 공개 ID로 선택한 면접 시간 조회 시 200 OK와 ID 리스트 반환")
    void getInterviewAvailable_Api_Success() throws Exception {
        // given
        String publicId = "test-public-id";

        InterviewAvailable mockAvailable = mock(InterviewAvailable.class);
        InterviewTime mockTime = mock(InterviewTime.class);

        given(mockAvailable.getInterviewTime()).willReturn(mockTime);
        given(mockTime.getId()).willReturn(1L);

        InterviewAvailableResult mockResult = InterviewAvailableResult.from(List.of(mockAvailable));
        given(interviewAvailableQueryService.getInterviewAvailable(anyString())).willReturn(mockResult);

        // when, then
        mockMvc.perform(get("/applications/{application-public-id}/interview-available", publicId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.interviewTimeIds[0]").value(1));
    }
}