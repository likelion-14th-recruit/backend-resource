package org.likelion.recruit.resource.interview.service.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.likelion.recruit.resource.common.init.ApplicationFixture.createCommand;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InterviewScheduleCommandServiceTest {

    @InjectMocks
    private InterviewScheduleCommandService interviewScheduleCommandService;

    @Mock
    private InterviewScheduleRepository interviewScheduleRepository;

    @Mock
    private InterviewAvailableRepository interviewAvailableRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    @DisplayName("날짜와 시간 세트가 모두 있고 지원자가 선택한 시간 내라면, 일정을 성공적으로 확정(저장)한다.")
    void upsertInterviewSchedule_FullSet_Success() {
        // Given
        String publicId = "app-test-1234";
        InterviewScheduleCommand command = createCommand(publicId, "J201"); // 날짜/시간 포함된 픽스처

        Application application = mock(Application.class);
        InterviewAvailable available = mock(InterviewAvailable.class);
        InterviewTime interviewTime = mock(InterviewTime.class);

        given(applicationRepository.findByPublicId(anyString())).willReturn(Optional.of(application));
        given(interviewAvailableRepository.findByApplicationAndDateTime(any(), any(), any(), any()))
                .willReturn(Optional.of(available));
        given(available.getInterviewTime()).willReturn(interviewTime);
        given(interviewScheduleRepository.findByApplication(any())).willReturn(Optional.empty());

        // When
        interviewScheduleCommandService.upsertInterviewSchedule(command);

        // Then
        verify(interviewScheduleRepository, times(1)).save(any(InterviewSchedule.class));
    }

    @Test
    @DisplayName("이미 일정이 존재하는 지원자의 경우, 새로운 정보로 업데이트(Update)를 수행한다.")
    void upsertInterviewSchedule_Update_Existing() {
        // Given
        String publicId = "app-test-1234";
        InterviewScheduleCommand command = createCommand(publicId, "새로운 장소");

        Application application = mock(Application.class);
        InterviewSchedule existingSchedule = mock(InterviewSchedule.class);
        InterviewAvailable available = mock(InterviewAvailable.class);
        InterviewTime interviewTime = mock(InterviewTime.class);

        given(applicationRepository.findByPublicId(anyString())).willReturn(Optional.of(application));
        given(interviewAvailableRepository.findByApplicationAndDateTime(any(), any(), any(), any()))
                .willReturn(Optional.of(available));
        given(available.getInterviewTime()).willReturn(interviewTime);
        given(interviewScheduleRepository.findByApplication(any())).willReturn(Optional.of(existingSchedule));

        // When
        interviewScheduleCommandService.upsertInterviewSchedule(command);

        // Then
        verify(existingSchedule, times(1)).assignPlace(eq("새로운 장소"));
        verify(existingSchedule, times(1)).updateInterviewTime(any());
        verify(interviewScheduleRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("지원자가 선택하지 않은 날짜/시간 조합으로 확정하려 하면 예외가 발생한다.")
    void upsertInterviewSchedule_Fail_Not_Available() {
        // Given
        String publicId = "app-test-1234";
        InterviewScheduleCommand command = createCommand(publicId, "J201");

        Application application = mock(Application.class);
        given(applicationRepository.findByPublicId(anyString())).willReturn(Optional.of(application));
        given(interviewAvailableRepository.findByApplicationAndDateTime(any(), any(), any(), any()))
                .willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> interviewScheduleCommandService.upsertInterviewSchedule(command))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("지원자가 선택한 면접 가능 시간이 아닙니다.");
    }
}