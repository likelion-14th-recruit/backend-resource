package org.likelion.recruit.resource.interview.service.query;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.interview.dto.result.InterviewScheduleResult;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InterviewScheduleQueryService {

    private InterviewScheduleRepository interviewScheduleRepository;
    private ApplicationRepository applicationRepository;

    public InterviewScheduleResult getInterviewSchedule(String applicationPublicId) {

        Application application=applicationRepository.findIdByPublicId(applicationPublicId)

    }
}
