package org.likelion.recruit.resource.interview.service.query;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableDetailResult;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableResult;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewAvailableQueryService {

    private final ApplicationRepository applicationRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;

    public InterviewAvailableResult getInterviewAvailable(String applicationPublicId) {
        Application application = applicationRepository.findByPublicId(applicationPublicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        List<InterviewAvailable> availables = interviewAvailableRepository.findAllByApplication(application);

        return InterviewAvailableResult.from(availables);
    }

    public InterviewAvailableDetailResult getInterviewAvailableDetail(String applicationPublicId) {

        Application application = applicationRepository.findByPublicId(applicationPublicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        List<InterviewAvailable> availables =
                interviewAvailableRepository.findAllByApplication(application);

        return InterviewAvailableDetailResult.from(availables);
    }

}
