package org.likelion.recruit.resource.recommend.dto.response;

import lombok.Getter;
import org.likelion.recruit.resource.recommend.dto.common.InterviewTimeAssignmentResponse;
import org.likelion.recruit.resource.recommend.dto.common.UnAssignedApplicationResponse;
import org.likelion.recruit.resource.recommend.dto.result.AssignmentResult;

import java.util.List;

@Getter
public class InterviewAssignmentResponse {

    private final List<InterviewTimeAssignmentResponse> assignments;
    private final List<UnAssignedApplicationResponse> unAssignedApplications;

    private InterviewAssignmentResponse(List<InterviewTimeAssignmentResponse> assignments,
            List<UnAssignedApplicationResponse> unAssignedApplications) {
        this.assignments = assignments;
        this.unAssignedApplications = unAssignedApplications;
    }

    public static InterviewAssignmentResponse from(AssignmentResult result) {
        return new InterviewAssignmentResponse(
                result.getAssignments().entrySet().stream()
                        .map(InterviewTimeAssignmentResponse::from)
                        .toList(),
                result.getUnAssignedApplications().stream()
                        .map(UnAssignedApplicationResponse::from)
                        .toList()
        );
    }
}

