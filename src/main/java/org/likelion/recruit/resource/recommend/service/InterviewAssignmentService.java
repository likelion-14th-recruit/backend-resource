package org.likelion.recruit.resource.recommend.service;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.common.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.common.engine.AssignmentEngineV1;
import org.likelion.recruit.resource.recommend.dto.result.AssignmentResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewAssignmentService {

    private final ApplicationRepository applicationRepository;
    private final InterviewTimeRepository interviewTimeRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;

    public AssignmentResult assignInterviewV1() {

        // 배정 대상 지원자 조회
        // submitted == true && passStatus == DOCUMENT_PASSED
        Set<Application> applications = applicationRepository.findInterviewTargets();


        // 고정된 면접 시간 조회
        List<InterviewTime> interviewTimes = interviewTimeRepository.findAllOrderByTime();

        // 가능 시간 맵 구성
        // Application -> 가능한 InterviewTime 집합
        Map<Application, Set<InterviewTime>> availabilityMap = interviewAvailableRepository.buildAvailabilityMap();


        // AssignmentContext 생성
        AssignmentContext context = new AssignmentContext(applications, interviewTimes, availabilityMap);

        // 배정 엔진 선택 및 실행
        AssignmentEngine assignmentEngine = new AssignmentEngineV1();
        assignmentEngine.assignV1(context);

        return AssignmentResult.from(context);
    }
}
