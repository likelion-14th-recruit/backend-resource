package org.likelion.recruit.resource.interview.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.dto.result.InterviewScheduleResult;
import org.likelion.recruit.resource.interview.service.command.InterviewScheduleCommandService;
import org.likelion.recruit.resource.interview.service.query.InterviewScheduleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
@WebMvcTest(InterviewScheduleController.class)
@Disabled
class InterviewScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockitoBean
    private InterviewScheduleCommandService interviewScheduleCommandService;

    @MockitoBean
    private InterviewScheduleQueryService interviewScheduleQueryService;

    private InterviewScheduleResult createMockResult(LocalDate date, LocalTime start, String place) throws Exception {
        Constructor<InterviewScheduleResult> constructor = InterviewScheduleResult.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InterviewScheduleResult result = constructor.newInstance();

        ReflectionTestUtils.setField(result, "date", date);
        ReflectionTestUtils.setField(result, "startTime", start);
        ReflectionTestUtils.setField(result, "endTime", start.plusMinutes(20));
        ReflectionTestUtils.setField(result, "place", place);
        return result;
    }

    @Test
    @DisplayName("날짜, 시작 시간이 갖춰지면 저장 후 최신 데이터를 반환한다.")
    void upsertInterviewSchedule_FullSet_Success() throws Exception {
        // Given
        String publicId = "app-test-1234";
        String requestJson = """
                {
                    "date": "2026-01-29",
                    "startTime": "14:00:00",
                    "place": "J201"
                }
                """;

        willDoNothing().given(interviewScheduleCommandService)
                .upsertInterviewSchedule(any(InterviewScheduleCommand.class));

        InterviewScheduleResult mockResult = createMockResult(
                LocalDate.of(2026, 1, 29),
                LocalTime.of(14, 0),
                "J201"
        );

        given(interviewScheduleQueryService.getInterviewSchedule(publicId))
                .willReturn(mockResult);

        // When & Then
        mockMvc.perform(post("/applications/{applicationPublicId}/interview-schedule/select", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.date").value("2026-01-29"))
                .andExpect(jsonPath("$.data.startTime").value("14:00:00"))
                .andExpect(jsonPath("$.data.endTime").value("14:20:00"))
                .andExpect(jsonPath("$.data.place").value("J201"))
                .andDo(print());
    }

    @Test
    @DisplayName("날짜(요일)만 선택하고 시작 시간 정보가 누락되면 VALIDATION_ERROR(400)가 발생한다.")
    void upsertInterviewSchedule_DateOnly_Fail() throws Exception {
        String publicId = "app-test-1234";
        String dateOnlyJson = """
                {
                    "date": "2026-01-29",
                    "place": "J201"
                }
                """;

        mockMvc.perform(post("/applications/{applicationPublicId}/interview-schedule/select", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dateOnlyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("면접 날짜와 시작 시간은 필수 입력 항목입니다."));
    }

    @Test
    @DisplayName("날짜 없이 시간 정보만 입력된 경우 VALIDATION_ERROR(400)가 발생한다.")
    void upsertInterviewSchedule_TimeOnly_Fail() throws Exception {
        String publicId = "app-test-1234";
        String timeOnlyJson = """
                {
                    "startTime": "14:00:00",
                    "place": "J201"
                }
                """;

        mockMvc.perform(post("/applications/{applicationPublicId}/interview-schedule/select", publicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeOnlyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("면접 날짜와 시작 시간은 필수 입력 항목입니다."));
    }
}