package org.likelion.recruit.resource.interview.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.request.InterviewAvailableRequest;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewAvailableCommandService {

    private final InterviewAvailableRepository interviewAvailableRepository;
    private final InterviewTimeRepository interviewTimeRepository;
    private final ApplicationRepository applicationRepository;

    /**
     *  연관관계 다 끊어버리고 id로만 저장해버리자. (갠프)
     *  그럼 application 객체, IntervieTime 객체 불러올 필요가 없음
     * */
    public void createInterviewAvailable(Long applicationId, List<Long> interviewTimeIds) {

        interviewAvailableRepository.deleteByApplicationId(applicationId);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        for (Long interviewTimeId : interviewTimeIds) {
            InterviewTime interviewTime = interviewTimeRepository.findById(interviewTimeId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.INTERVIEW_TIME_NOT_EXISTS));

            InterviewAvailable interviewAvailable = InterviewAvailable.create(interviewTime, application);
            interviewAvailableRepository.save(interviewAvailable);
        }
    }
}
