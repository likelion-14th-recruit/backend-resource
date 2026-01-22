package org.likelion.recruit.resource.recommend.dto.result;

import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AssignmentResult {

    private final Map<InterviewTime, List<Application>> assignments;
    private final List<Application> unAssignedApplications;

    private AssignmentResult(Map<InterviewTime, List<Application>> assignments,
            List<Application> unAssignedApplications) {
        this.assignments = assignments;
        this.unAssignedApplications = unAssignedApplications;
    }

    public static AssignmentResult from(AssignmentContext context) {

        Map<InterviewTime, List<Application>> assignments = new HashMap<>();

        for (InterviewTime time : context.getInterviewTimes()) {
            assignments.put(time, new ArrayList<>(context.getAssignedApplications(time)));
        }

        return new AssignmentResult(assignments, new ArrayList<>(context.getUnAssignedApplications()));
    }
}

