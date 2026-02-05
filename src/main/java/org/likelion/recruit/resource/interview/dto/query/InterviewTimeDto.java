package org.likelion.recruit.resource.interview.dto.query;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class InterviewTimeDto {
    private Long interviewTimeId;
    private LocalTime startTime;
    private LocalTime endTime;

    public InterviewTimeDto(Long interviewTimeId, LocalTime startTime, LocalTime endTime) {
        this.interviewTimeId = interviewTimeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
