package org.likelion.recruit.resource.interview.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewAvailableResponse {

    private List<Long> interviewTimeIds;

    public static InterviewAvailableResponse from(InterviewAvailableResult result) {
        return new InterviewAvailableResponse(result.getInterviewTimeIds());
    }
}
