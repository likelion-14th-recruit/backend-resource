package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.SendDocumentResultDto;

@Getter
@Builder
public class SendDocumentResultResponse {
    private Integer documentPassedCount;
    private Integer documentFailedCount;

    public static SendDocumentResultResponse from(SendDocumentResultDto result) {
        return SendDocumentResultResponse.builder()
                .documentPassedCount(result.getDocumentPassedCount())
                .documentFailedCount(result.getDocumentFailedCount())
                .build();
    }
}
