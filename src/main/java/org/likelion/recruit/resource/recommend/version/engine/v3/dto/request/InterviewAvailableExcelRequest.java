package org.likelion.recruit.resource.recommend.version.engine.v3.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.recommend.dto.request.InterviewTimeAssignmentRequest;
import org.likelion.recruit.resource.recommend.dto.request.UnAssignedApplicationRequest;

import java.util.List;

@Getter
@NoArgsConstructor
public class InterviewAvailableExcelRequest
{
        private List<InterviewTimeAssignmentRequest> assignments;
        private List<UnAssignedApplicationRequest> unAssignedApplications;
}
