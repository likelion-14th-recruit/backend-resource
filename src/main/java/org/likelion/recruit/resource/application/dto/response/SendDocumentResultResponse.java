package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.SendMessageResultDto;

@Getter
@Builder
public class SendDocumentResultResponse {
    private Integer documentPassedCount;
    private Integer documentFailedCount;

    public static SendDocumentResultResponse from(SendMessageResultDto result) {
        return SendDocumentResultResponse.builder()
                .documentPassedCount(result.getPassedCount())
                .documentFailedCount(result.getFailedCount())
                .build();
    }
}
