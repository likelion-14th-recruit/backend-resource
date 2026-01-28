package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;

@Getter
public class AnswersRefactorResult {
    private Long questionId;
    private Integer questionNumber;
    private String content;

    public AnswersRefactorResult(Long questionId, Integer questionNumber, String content) {
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.content = content;
    }
}
