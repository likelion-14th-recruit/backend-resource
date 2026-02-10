package org.likelion.recruit.resource.interview.dto.request;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class InterviewScheduleRequest {
    private LocalDate date;
    private LocalTime startTime;
    private String place;

    @AssertTrue(message = "면접 날짜와 시작 시간은 필수 입력 항목입니다.")
    public boolean isValid() {
        return date != null && startTime != null;
    }
}