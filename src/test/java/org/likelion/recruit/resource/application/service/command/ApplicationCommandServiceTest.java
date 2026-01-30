package org.likelion.recruit.resource.application.service.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.application.repository.QuestionRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.common.init.ApplicationFixture;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.List;

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

    private final String publicId = "test-public-id";

    @Test
    @DisplayName("백엔드 제출 성공 - 선택 질문(DEVELOPMENT) 미응답 시에도 성공")
    void submitApplication_Backend_Success() {
        // given
        Application backendApp = ApplicationFixture.createApplication(publicId);
        ReflectionTestUtils.setField(backendApp, "part", Part.BACKEND);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(backendApp));
        given(interviewAvailableRepository.existsByApplication(backendApp)).willReturn(true);

        Question q1 = Question.create(1, "공통 질문", Question.Type.COMMON);
        ReflectionTestUtils.setField(q1, "id", 1L);
        Question q6 = Question.create(6, "깃허브 주소(선택)", Question.Type.DEVELOPMENT);
        ReflectionTestUtils.setField(q6, "id", 6L);
        given(questionRepository.findByTypeIn(any())).willReturn(List.of(q1, q6));

        Answer a1 = Answer.create("백엔드 공통 답변", q1, backendApp);
        given(answerRepository.findAllByApplicationWithQuestion(backendApp)).willReturn(List.of(a1));

        // when
        applicationCommandService.submitApplication(publicId);

        // then
        assertThat(backendApp.isSubmitted()).isTrue();
        assertThat(backendApp.getSubmittedAt()).isNotNull();
    }

    @Test
    @DisplayName("프론트엔드 제출 성공 - 선택 질문 미응답 시에도 성공")
    void submitApplication_Frontend_Success() {
        // given
        Application frontendApp = ApplicationFixture.createApplication(publicId);
        ReflectionTestUtils.setField(frontendApp, "part", Part.FRONTEND);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(frontendApp));
        given(interviewAvailableRepository.existsByApplication(frontendApp)).willReturn(true);

        Question q1 = Question.create(1, "공통", Question.Type.COMMON);
        ReflectionTestUtils.setField(q1, "id", 1L);
        Question q6 = Question.create(6, "선택", Question.Type.DEVELOPMENT);
        ReflectionTestUtils.setField(q6, "id", 6L);
        given(questionRepository.findByTypeIn(any())).willReturn(List.of(q1, q6));

        Answer a1 = Answer.create("답변내용", q1, frontendApp);
        given(answerRepository.findAllByApplicationWithQuestion(frontendApp)).willReturn(List.of(a1));

        // when
        applicationCommandService.submitApplication(publicId);

        // then
        assertThat(frontendApp.isSubmitted()).isTrue();
    }

    @Test
    @DisplayName("디자인 제출 실패 - 필수 질문(PRODUCT_DESIGN) 누락 시 예외 발생")
    void submitApplication_Design_Fail() {
        // given
        Application designApp = ApplicationFixture.createApplication(publicId);
        ReflectionTestUtils.setField(designApp, "part", Part.PRODUCT_DESIGN);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(designApp));
        given(interviewAvailableRepository.existsByApplication(designApp)).willReturn(true);

        Question q1 = Question.create(1, "공통", Question.Type.COMMON);
        ReflectionTestUtils.setField(q1, "id", 1L);
        Question q5 = Question.create(5, "디자인필수", Question.Type.PRODUCT_DESIGN);
        ReflectionTestUtils.setField(q5, "id", 5L);
        given(questionRepository.findByTypeIn(any())).willReturn(List.of(q1, q5));

        Answer a1 = Answer.create("공통만답변", q1, designApp);
        given(answerRepository.findAllByApplicationWithQuestion(designApp)).willReturn(List.of(a1));

        // when & then
        assertThatThrownBy(() -> applicationCommandService.submitApplication(publicId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.APPLICATION_INCOMPLETE);
    }

    @Test
    @DisplayName("이미 제출된 지원서인 경우 예외 발생")
    void submitApplication_AlreadySubmitted() {
        // given
        Application submittedApp = ApplicationFixture.createApplication(publicId);
        ReflectionTestUtils.setField(submittedApp, "submitted", true);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(submittedApp));

        // when & then
        assertThatThrownBy(() -> applicationCommandService.submitApplication(publicId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.APPLICATION_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("면접 시간을 선택하지 않은 경우 예외 발생")
    void submitApplication_NoInterviewTime() {
        // given
        Application app = ApplicationFixture.createApplication(publicId);
        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(app));
        given(interviewAvailableRepository.existsByApplication(app)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> applicationCommandService.submitApplication(publicId))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INTERVIEW_TIME_NOT_EXISTS);
    }
}