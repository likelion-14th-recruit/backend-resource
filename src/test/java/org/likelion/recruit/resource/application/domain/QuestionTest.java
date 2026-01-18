package org.likelion.recruit.resource.application.domain;

import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Question.Type;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionTest {

    @Test
    void createQuestion() {
        Integer commonNumber = 1;
        String commonContent = "공통 질문";
        Type commonType = Type.COMMON;

        Question question = Question.create(commonNumber, commonContent, commonType);

        assertThat(question.getQuestionNumber()).isEqualTo(commonNumber);
        assertThat(question.getContent()).isEqualTo(commonContent);
        assertThat(question.getType()).isEqualTo(commonType);

    }


}