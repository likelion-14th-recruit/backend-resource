package org.likelion.recruit.resource.interview.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewAvailableResponse {

    private List<Long> interviewTimeIds;

    private InterviewAvailableResponse(List<Long> interviewTimeIds) {
        this.interviewTimeIds = interviewTimeIds;
    }

    public static InterviewAvailableResponse from(InterviewAvailableResult result) {
        return new InterviewAvailableResponse(result.getInterviewTimeIds());
    }
}
