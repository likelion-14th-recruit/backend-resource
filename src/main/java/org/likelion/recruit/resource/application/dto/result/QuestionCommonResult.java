package org.likelion.recruit.resource.application.dto.result;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.query.QuestionCommonDto;

import java.util.List;

@Getter
@Builder
public class QuestionCommonResult {

    private List<QuestionCommonDto> questions;

    public static QuestionCommonResult from(List<QuestionCommonDto> questions){
        return QuestionCommonResult.builder()
                .questions(questions)
                .build();
    }
}
