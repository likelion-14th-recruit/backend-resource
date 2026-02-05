package org.likelion.recruit.resource.application.dto.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswersResult {
    private List<AnswerInfo> answers;

    @Getter
    @AllArgsConstructor
    public static class AnswerInfo {
        private Long questionId;
        private String content;

        public static AnswerInfo from(Answer answer) {
            return new AnswerInfo(
                    answer.getQuestion().getId(),
                    answer.getContent()
            );
        }
    }

    public static AnswersResult from(List<Answer> answers) {
        return AnswersResult.builder()
                .answers(answers.stream()
                        .map(AnswerInfo::from)
                        .toList())
                .build();
    }
}