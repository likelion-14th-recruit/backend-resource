package org.likelion.recruit.resource.application.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.application.repository.QuestionRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApplicationCommandServiceTest {

    @InjectMocks
    private ApplicationCommandService applicationCommandService;

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private InterviewAvailableRepository interviewAvailableRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerRepository answerRepository;

    private Application application;
    private final String publicId = "test-public-id";

    @BeforeEach
    void setUp() {
        application = Application.create(
                "이름", "학번", "01012345678", "password",
                "전공", "복수전공", 7, Application.AcademicStatus.ENROLLED, Part.BACKEND
        );
        ReflectionTestUtils.setField(application, "publicId", publicId);
        ReflectionTestUtils.setField(application, "submitted", false);
    }

    @Test
    @DisplayName("지원서 제출 성공")
    void submitApplication_Success() {
        // given
        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(application));
        given(interviewAvailableRepository.existsByApplication(application)).willReturn(true);
        given(questionRepository.countByTypeIn(anyList())).willReturn(5L);
        given(answerRepository.countByApplicationAndQuestionTypeIn(eq(application), anyList()))
                .willReturn(5L);

        // when
        applicationCommandService.submitApplication(publicId);

        // then
        assertThat(application.isSubmitted()).isTrue();
        assertThat(application.getSubmittedAt()).isNotNull();
    }

    @Test
    @DisplayName("이미 제출된 지원서인 경우 예외 발생")
    void submitApplication_AlreadySubmitted() {
        // given
        application.submit();
        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(application));

        // when & then
        assertThatThrownBy(() -> applicationCommandService.submitApplication(publicId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.APPLICATION_ALREADY_EXISTS.getMessage());
    }

    @Test
    @DisplayName("필수 답변 개수가 부족한 경우 예외 발생")
    void submitApplication_IncompleteAnswers() {
        // given
        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(application));
        given(interviewAvailableRepository.existsByApplication(application)).willReturn(true);
        given(questionRepository.countByTypeIn(anyList())).willReturn(5L);
        given(answerRepository.countByApplicationAndQuestionTypeIn(eq(application), anyList()))
                .willReturn(4L);

        // when & then
        assertThatThrownBy(() -> applicationCommandService.submitApplication(publicId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.APPLICATION_INCOMPLETE.getMessage());
    }

    @Test
    @DisplayName("면접 시간을 선택하지 않은 경우 예외 발생")
    void submitApplication_NoInterviewTime() {
        // given
        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(application));
        given(interviewAvailableRepository.existsByApplication(application)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> applicationCommandService.submitApplication(publicId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.INTERVIEW_TIME_NOT_EXISTS.getMessage());
    }
}