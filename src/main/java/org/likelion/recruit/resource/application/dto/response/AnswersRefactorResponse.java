package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.AnswersRefactorResult;

import java.util.List;

@Getter
@Builder
public class AnswersRefactorResponse {
    List<AnswersRefactorResult> questions;

    public static AnswersRefactorResponse from(List<AnswersRefactorResult> results){
        return AnswersRefactorResponse.builder()
                .questions(results)
                .build();
    }
}
