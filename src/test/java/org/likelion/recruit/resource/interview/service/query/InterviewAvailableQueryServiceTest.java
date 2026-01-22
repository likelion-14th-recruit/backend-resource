package org.likelion.recruit.resource.interview.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InterviewAvailableQueryServiceTest {

    @InjectMocks
    private InterviewAvailableQueryService interviewAvailableQueryService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private InterviewAvailableRepository interviewAvailableRepository;

    @Test
    @DisplayName("지원서 Public ID로 선택한 면접 가능 시간 목록을 조회하여 DTO로 반환")
    void getInterviewAvailable_Success() {
        // given
        String publicId = "test-public-id";
        Application mockApplication = mock(Application.class);

        // Mock 데이터 세팅 (Entity -> DTO 변환 과정 검증용)
        InterviewTime mockTime = mock(InterviewTime.class);
        given(mockTime.getId()).willReturn(100L);

        InterviewAvailable mockAvailable = mock(InterviewAvailable.class);
        given(mockAvailable.getInterviewTime()).willReturn(mockTime);

        given(applicationRepository.findByPublicId(publicId)).willReturn(Optional.of(mockApplication));
        given(interviewAvailableRepository.findAllByApplication(mockApplication)).willReturn(List.of(mockAvailable));

        // when
        InterviewAvailableResult result = interviewAvailableQueryService.getInterviewAvailable(publicId);

        // then
        assertThat(result.getInterviewTimeIds()).hasSize(1);
        assertThat(result.getInterviewTimeIds().get(0)).isEqualTo(100L);
    }
}