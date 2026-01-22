package org.likelion.recruit.resource.recommend.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class InterviewTimeAssignmentResponse {

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    private final List<AssignedApplicationResponse> assignedApplications;

    private InterviewTimeAssignmentResponse(LocalDate date, LocalTime startTime,
                                            LocalTime endTime, List<AssignedApplicationResponse> assignedApplications) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignedApplications = assignedApplications;
    }

    public static InterviewTimeAssignmentResponse from(Map.Entry<InterviewTime, List<Application>> entry) {
        InterviewTime time = entry.getKey();

        return InterviewTimeAssignmentResponse.builder()
                .date(time.getDate())
                .startTime(time.getStartTime())
                .endTime(time.getEndTime())
                .assignedApplications(entry.getValue().stream()
                        .map(AssignedApplicationResponse::from)
                        .toList())
                .build();
    }
}

