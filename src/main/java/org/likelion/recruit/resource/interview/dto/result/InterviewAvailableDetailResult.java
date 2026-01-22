package org.likelion.recruit.resource.interview.dto.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewAvailableDetailResult {
    private List<InterviewTime> interviewTimes;

    private InterviewAvailableDetailResult(List<InterviewTime> interviewTimes) {
        this.interviewTimes = interviewTimes;
    }

    public static InterviewAvailableDetailResult from(List<InterviewAvailable> availables) {
        return new InterviewAvailableDetailResult(
                availables.stream()
                        .map(InterviewAvailable::getInterviewTime)
                        .toList()
        );
    }
}
