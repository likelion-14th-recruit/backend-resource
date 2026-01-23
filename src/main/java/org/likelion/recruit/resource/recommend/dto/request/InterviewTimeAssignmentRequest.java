package org.likelion.recruit.resource.recommend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class InterviewTimeAssignmentRequest {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private List<AssignedApplicationRequest> assignedApplications;
}

