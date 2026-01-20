package org.likelion.recruit.resource.application.dto.result;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.query.QuestionCommonDto;

import java.util.List;

@Getter
@Builder
public class QuestionsResult {

    private List<QuestionCommonDto> questions;

    public static QuestionsResult from(List<QuestionCommonDto> questions){
        return QuestionsResult.builder()
                .questions(questions)
                .build();
    }
}
