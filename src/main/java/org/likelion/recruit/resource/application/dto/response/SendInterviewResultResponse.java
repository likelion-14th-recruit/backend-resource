package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.SendMessageResultDto;

@Getter
@Builder
public class SendInterviewResultResponse {
    private Integer interviewPassedCount;
    private Integer interviewFailedCount;

    public static SendInterviewResultResponse from(SendMessageResultDto result) {
        return SendInterviewResultResponse.builder()
                .interviewPassedCount(result.getPassedCount())
                .interviewFailedCount(result.getFailedCount())
                .build();
    }
}
