package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;

@Getter
public class SendMessageResultDto {
    private Integer passedCount;
    private Integer failedCount;

    private SendMessageResultDto(Integer passedCount, Integer failedCount) {
        this.passedCount = passedCount;
        this.failedCount = failedCount;
    }

    public static SendMessageResultDto of(Integer documentPassedCount, Integer documentFailedCount) {
        return new SendMessageResultDto(documentPassedCount, documentFailedCount);
    }
}
