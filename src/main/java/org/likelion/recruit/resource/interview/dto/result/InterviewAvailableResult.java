package org.likelion.recruit.resource.interview.dto.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewAvailableResult {
    private List<Long> interviewTimeIds;

    private InterviewAvailableResult(List<Long> interviewTimeIds) {
        this.interviewTimeIds = interviewTimeIds;
    }

    public static InterviewAvailableResult from(List<InterviewAvailable> availables) {
        return new InterviewAvailableResult(
                availables.stream()
                        .map(available -> available.getInterviewTime().getId())
                        .toList()
        );
    }
}
