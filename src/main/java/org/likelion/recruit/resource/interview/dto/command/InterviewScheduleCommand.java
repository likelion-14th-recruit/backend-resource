package org.likelion.recruit.resource.interview.dto.command;

import lombok.Getter;
import lombok.Builder;
import org.likelion.recruit.resource.interview.dto.request.InterviewScheduleRequest;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class InterviewScheduleCommand {
    private String applicationPublicId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String place;

    public static InterviewScheduleCommand of(String publicId, InterviewScheduleRequest request) {
        return InterviewScheduleCommand.builder()
                .applicationPublicId(publicId)
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .place(request.getPlace())
                .build();
    }
}