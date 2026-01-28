package org.likelion.recruit.resource.interview.dto.request;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterviewScheduleRequest {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String place;

    @AssertTrue(message = "면접 날짜와 시작/종료 시간은 모두 입력해야 저장 가능합니다.")
    public boolean isValid() {
        return date != null && startTime != null && endTime != null;
    }
}