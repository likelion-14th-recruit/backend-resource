package org.likelion.recruit.resource.application.dto.result;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.likelion.recruit.resource.application.domain.Answer;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswersResult {
    private List<AnswerInfo> answers;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AnswerInfo {
        private Long questionId;
        private String content;

        @QueryProjection
        public AnswerInfo(Long questionId, String content) {
            this.questionId = questionId;
            this.content = content;
        }

        public static AnswerInfo from(Answer answer) {
            return new AnswerInfo(
                    answer.getQuestion().getId(),
                    answer.getContent()
            );
        }
    }

    public static AnswersResult of(List<AnswerInfo> answerInfos) {
        return AnswersResult.builder()
                .answers(answerInfos)
                .build();
    }
}