package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;

@Getter
public class SendDocumentResultDto {
    private Integer DocumentPassedCount;
    private Integer DocumentFailedCount;

    private SendDocumentResultDto(Integer documentPassedCount, Integer documentFailedCount) {
        DocumentPassedCount = documentPassedCount;
        DocumentFailedCount = documentFailedCount;
    }

    public static SendDocumentResultDto of(Integer documentPassedCount, Integer documentFailedCount) {
        return new SendDocumentResultDto(documentPassedCount, documentFailedCount);
    }
}
