package org.likelion.recruit.resource.recommend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InterviewAssignmentValidationRequest {

    private List<InterviewTimeAssignmentRequest> assignments;
    private List<UnAssignedApplicationRequest> unAssignedApplications;
}
