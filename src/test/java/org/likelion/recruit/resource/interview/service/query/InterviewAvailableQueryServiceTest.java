package org.likelion.recruit.resource.interview.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.init.ApplicationFixture;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
class InterviewAvailableQueryServiceTest {

    @InjectMocks
    private InterviewAvailableQueryService interviewAvailableQueryService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private InterviewAvailableRepository interviewAvailableRepository;

    @Test
    @DisplayName("지원서 Public ID로 선택한 면접 가능 시간 목록을 조회하여 DTO로 반환 (최종 수정본)")
    void getInterviewAvailable_Success() {
        // given
        String publicId = "test-public-id";
        Application app = ApplicationFixture.createApplication(publicId);
        InterviewTime time = ApplicationFixture.createInterviewTime(100L);
        InterviewAvailable available = ApplicationFixture.createAvailable(app, time);

        given(applicationRepository.findByPublicId(publicId))
                .willReturn(Optional.of(app));
        given(interviewAvailableRepository.findAllByApplication(app))
                .willReturn(List.of(available));

        // when
        InterviewAvailableResult result = interviewAvailableQueryService.getInterviewAvailable(publicId);

        // then
        assertThat(result.getInterviewTimeIds()).hasSize(1);
        assertThat(result.getInterviewTimeIds().get(0)).isEqualTo(100L);

        // verify
        verify(applicationRepository).findByPublicId(publicId);
        verify(interviewAvailableRepository).findAllByApplication(app);
    }
}