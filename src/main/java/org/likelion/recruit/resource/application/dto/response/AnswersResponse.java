package org.likelion.recruit.resource.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;

import java.util.List;

@Getter
@Builder
public class AnswersResponse {
    private String applicationPublicId;
    private List<AnswerDetail> answers;

    @Getter
    @AllArgsConstructor
    public static class AnswerDetail {
        private Long questionId;
        private String content;
    }

    //Result -> Response
    public static AnswersResponse from(AnswersResult result) {
        return AnswersResponse.builder()
                .applicationPublicId(result.getApplicationPublicId())
                .answers(result.getAnswers().stream()
                        .map(info -> new AnswerDetail(info.getQuestionId(), info.getContent()))
                        .toList())
                .build();
    }
}
