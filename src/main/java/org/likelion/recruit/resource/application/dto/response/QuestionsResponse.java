package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.query.QuestionCommonDto;
import org.likelion.recruit.resource.application.dto.result.QuestionsResult;

import java.util.List;

@Getter
@Builder
public class QuestionsResponse {
    private List<QuestionCommonDto> questions;

    public static QuestionsResponse from(QuestionsResult result) {
        return  QuestionsResponse.builder()
                .questions(result.getQuestions())
                .build();
    }
}
