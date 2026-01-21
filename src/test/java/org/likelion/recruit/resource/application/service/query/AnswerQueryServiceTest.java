package org.likelion.recruit.resource.application.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AnswerQueryServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerQueryService answerQueryService;

    @Test
    @DisplayName("공개 ID로 지원서와 답변 목록을 성공적으로 조회")
    void getAnswers_Success() {
        // Given
        String targetPublicId = "app-7c4ec3c9-c31f-4e31-bd48-a4377ea63850";

        // Question 생성
        Question mockQuestion = Question.create(1, "지원 동기", Question.Type.COMMON);
        ReflectionTestUtils.setField(mockQuestion, "id", 1L);

        // Application 생성
        Application mockApplication = Application.create(
                "성민", "20200129", "01041019429", "hash123",
                "국어국문학과", "융합소프트웨어전공", 7,
                Application.AcademicStatus.ENROLLED, Part.BACKEND
        );
        ReflectionTestUtils.setField(mockApplication, "publicId", targetPublicId);

        // Answer 생성 및 리스트화
        Answer mockAnswer = Answer.create("열심히 하겠습니다!", mockQuestion, mockApplication);
        List<Answer> mockAnswers = List.of(mockAnswer);

        // 레포지토리 메서드 매칭

        // 지원서 조회 Mock
        given(applicationRepository.findByPublicId(targetPublicId))
                .willReturn(Optional.of(mockApplication));

        // 답변 목록 조회 Mock
        given(answerRepository.findAllByApplication(mockApplication))
                .willReturn(mockAnswers);

        // When
        AnswersResult result = answerQueryService.getAnswers(targetPublicId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getApplicationPublicId()).isEqualTo(targetPublicId);
        assertThat(result.getAnswers()).hasSize(1);
        assertThat(result.getAnswers().get(0).getContent()).isEqualTo("열심히 하겠습니다!");
    }
}