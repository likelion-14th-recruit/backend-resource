package org.likelion.recruit.resource.application.dto.query;

import lombok.Getter;

@Getter
public class QuestionCommonDto {
    private Long questionId;
    private Integer questionNumber;
    private String content;

    public QuestionCommonDto(Long questionId, Integer questionNumber, String content) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.content = content;
    }
}
