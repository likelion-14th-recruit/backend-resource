package org.likelion.recruit.resource.interview.service.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterviewScheduleCommandServiceTest {

    @InjectMocks
    private InterviewScheduleCommandService interviewScheduleCommandService;

    @Mock
    private InterviewScheduleRepository interviewScheduleRepository;

    @Mock
    private InterviewTimeRepository interviewTimeRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Test
    @DisplayName("서버에 등록된 면접 시간이라면, 지원자 선택 여부와 상관없이 일정을 성공적으로 확정한다.")
    void upsertInterviewSchedule_Success() {
        // Given
        String publicId = "app-test-123";
        InterviewScheduleCommand command = createCommand(publicId, LocalDate.of(2026, 3, 9), LocalTime.of(18, 0));

        Application mockApplication = mock(Application.class);
        InterviewTime mockTime = mock(InterviewTime.class);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(mockApplication));
        given(interviewTimeRepository.findByDateAndStartTime(any(), any())).willReturn(Optional.of(mockTime));
        given(interviewScheduleRepository.findByApplication(mockApplication)).willReturn(Optional.empty());

        // When
        interviewScheduleCommandService.upsertInterviewSchedule(command);

        // Then
        verify(interviewScheduleRepository, times(1)).save(any(InterviewSchedule.class));
    }

    @Test
    @DisplayName("이미 일정이 존재하는 지원자의 경우, 새로운 정보로 업데이트(Update)를 수행한다.")
    void upsertInterviewSchedule_Update_Success() {
        // Given
        String publicId = "app-test-123";
        InterviewScheduleCommand command = createCommand(publicId, LocalDate.of(2026, 3, 9), LocalTime.of(18, 0));

        Application mockApplication = mock(Application.class);
        InterviewTime mockTime = mock(InterviewTime.class);
        InterviewSchedule existingSchedule = mock(InterviewSchedule.class);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(mockApplication));
        given(interviewTimeRepository.findByDateAndStartTime(any(), any())).willReturn(Optional.of(mockTime));
        given(interviewScheduleRepository.findByApplication(mockApplication)).willReturn(Optional.of(existingSchedule));

        // When
        interviewScheduleCommandService.upsertInterviewSchedule(command);

        // Then
        verify(existingSchedule, times(1)).updateInterviewTime(mockTime);
        verify(interviewScheduleRepository, never()).save(any());
    }

    @Test
    @DisplayName("서버에 존재하지 않는 시간대로 확정하려 하면 예외가 발생한다.")
    void upsertInterviewSchedule_InvalidTime_Fail() {
        // Given
        String publicId = "app-test-123";
        InterviewScheduleCommand command = createCommand(publicId, LocalDate.of(2099, 1, 1), LocalTime.of(0, 0));

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(mock(Application.class)));
        given(interviewTimeRepository.findByDateAndStartTime(any(), any())).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> interviewScheduleCommandService.upsertInterviewSchedule(command))
                .isInstanceOf(BusinessException.class);
    }

    private InterviewScheduleCommand createCommand(String publicId, LocalDate date, LocalTime start) {
        return InterviewScheduleCommand.builder()
                .applicationPublicId(publicId)
                .date(date)
                .startTime(start)
                .place("J201")
                .build();
    }
}