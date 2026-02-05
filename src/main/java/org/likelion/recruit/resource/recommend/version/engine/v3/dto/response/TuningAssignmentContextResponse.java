package org.likelion.recruit.resource.recommend.version.engine.v3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.likelion.recruit.resource.recommend.dto.response.InterviewAssignmentResponse;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;

@Data
@AllArgsConstructor
@Builder
public class TuningAssignmentContextResponse {
    private WeightTuningRequest weightTuningRequest;
    private InterviewAssignmentResponse interviewAssignmentResponse;

    public static TuningAssignmentContextResponse of(
            WeightTuningRequest weightTuningRequest,
            InterviewAssignmentResponse interviewAssignmentResponse){
        return TuningAssignmentContextResponse.builder()
                .weightTuningRequest(weightTuningRequest)
                .interviewAssignmentResponse(interviewAssignmentResponse)
                .build();
    }
}
