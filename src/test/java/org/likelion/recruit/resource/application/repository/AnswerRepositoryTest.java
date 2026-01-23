package org.likelion.recruit.resource.application.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.common.config.QuerydslConfig;
import org.likelion.recruit.resource.common.init.ApplicationFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(QuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("지원서로 답변 목록 조회 시 질문 정보까지 Fetch Join으로 가져옴")
    void findAllByApplicationWithQuestion_Success() {
        // given
        Application app = ApplicationFixture.createApplication("test-public-id");
        Question q = ApplicationFixture.createQuestion();
        Answer a = Answer.create("테스트 답변 내용입니다.", q, app);

        em.persist(app);
        em.persist(q);
        em.persist(a);

        em.flush();
        em.clear();

        // when
        List<Answer> results = answerRepository.findAllByApplicationWithQuestion(app);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getQuestion().getContent()).isEqualTo("지원 동기");
    }

    @Test
    @DisplayName("지원서와 질문 타입 리스트로 해당하는 답변 개수를 카운트")
    void countByApplicationAndQuestionTypeIn_Success() {
        // given
        Application app = ApplicationFixture.createApplication("test-public-id");
        em.persist(app);

        Question q1 = Question.create(1, "공통 질문", Question.Type.COMMON);
        Question q2 = Question.create(2, "프론트/백엔드 질문", Question.Type.DEVELOPMENT);
        em.persist(q1);
        em.persist(q2);

        em.persist(Answer.create("답변1", q1, app));
        em.persist(Answer.create("답변2", q2, app));

        em.flush();
        em.clear();

        // when
        long count = answerRepository.countByApplicationAndQuestionTypeIn(
                app, List.of(Question.Type.COMMON)
        );

        // then
        assertThat(count).isEqualTo(1L);
    }
}