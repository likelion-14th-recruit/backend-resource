package org.likelion.recruit.resource.interview.dto.query;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class InterviewTimeQueryDto {
    private Long interviewTimeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public InterviewTimeQueryDto(Long interviewTimeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.interviewTimeId = interviewTimeId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
