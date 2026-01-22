package org.likelion.recruit.resource.application.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswersResponse {
    private List<AnswerDetail> answers;

    public static AnswersResponse from(AnswersResult result) {
        return AnswersResponse.builder()
                .answers(result.getAnswers().stream()
                        .map(AnswerDetail::from)
                        .toList())
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class AnswerDetail {
        private Long questionId;
        private String content;

        public static AnswerDetail from(AnswersResult.AnswerInfo info) {
            return new AnswerDetail(info.getQuestionId(), info.getContent());
        }
    }
}