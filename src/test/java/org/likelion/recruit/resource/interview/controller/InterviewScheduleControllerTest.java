package org.likelion.recruit.resource.interview.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.interview.dto.result.InterviewScheduleResult;
import org.likelion.recruit.resource.interview.service.command.InterviewScheduleCommandService;
import org.likelion.recruit.resource.interview.service.query.InterviewScheduleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InterviewScheduleController.class)
class InterviewScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockitoBean
    private InterviewScheduleCommandService interviewScheduleCommandService;

    @MockitoBean
    private InterviewScheduleQueryService interviewScheduleQueryService;

    @Test
    @DisplayName("날짜와 시작 시간이 포함된 JSON을 던지면 200 OK를 반환한다.")
    void upsertInterviewSchedule_Success() throws Exception {
        // Given
        String publicId = "app-test-123";
        String requestJson = """
                {
                    "date": "2026-03-09",
                    "startTime": "18:00:00",
                    "place": "J201"
                }
                """;


        willDoNothing().given(interviewScheduleCommandService)
                .upsertInterviewSchedule(any());


        InterviewScheduleResult mockResult = createMockResult(
                LocalDate.of(2026, 3, 9),
                LocalTime.of(18, 0),
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
                .andExpect(jsonPath("$.data.date").value("2026-03-09"))
                .andExpect(jsonPath("$.data.endTime").value("18:20"))
                .andDo(print());
    }

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
}