package org.likelion.recruit.resource.answer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.question.domain.Question;
import org.likelion.recruit.resource.question.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void createAnswer() {

        // question 생성
        Integer commonNumber = 1;
        String commonContent = "공통 질문";
        Question.Type commonType = Question.Type.COMMON;

        Question question = Question.create(commonNumber, commonContent, commonType);

        // application 생성
        String name = "김지오";
        String studentNumber = "20201223";
        String phoneNumber = "01012345678";
        String passwordHash = "hashed-password";
        String major = "수학과";
        String doubleMajor = "복수전공";
        Integer semester = 7;
        Application.AcademicStatus status = Application.AcademicStatus.ENROLLED;
        Part part = Part.BACKEND;

        Application application = Application.create(name, studentNumber, phoneNumber,
                passwordHash, major, doubleMajor, semester, status, part);

        Answer answer = Answer.create("답변", question, application);

        Assertions.assertThat(answer).isNotNull();
        Assertions.assertThat(answer.getQuestion().getQuestionNumber()).isEqualTo(commonNumber);
        Assertions.assertThat(answer.getApplication().getPart()).isEqualTo(part);
    }
}