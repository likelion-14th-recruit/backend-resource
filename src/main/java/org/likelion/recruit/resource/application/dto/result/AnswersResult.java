package org.likelion.recruit.resource.application.dto.result;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Answer;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswersResult {
    private String applicationPublicId;
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

    //서비스 조회 데이터 -> Result 객체 변환
    public static AnswersResult of(String publicId, List<AnswerInfo> answers) {
        return AnswersResult.builder()
                .applicationPublicId(publicId)
                .answers(answers)
                .build();
    }
}