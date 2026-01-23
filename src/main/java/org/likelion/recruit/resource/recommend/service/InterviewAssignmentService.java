package org.likelion.recruit.resource.recommend.service;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.version.engine.ScoringModel;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v1.AssignmentEngineV1;
import org.likelion.recruit.resource.recommend.version.engine.v2.AssignmentEngineV2;
import org.likelion.recruit.resource.recommend.version.engine.v2.ScoringModelV2;
import org.likelion.recruit.resource.recommend.dto.result.AssignmentResult;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.EvaluationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewAssignmentService {

    private static final int V3_TRY_COUNT = 500;

    private final ApplicationRepository applicationRepository;
    private final InterviewTimeRepository interviewTimeRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;
    private final AssignmentEngine assignmentEngine;

    public AssignmentResult assignInterviewV1() {

        AssignmentContext context = buildAssignmentContext();

        // 배정 엔진 선택 및 실행
        AssignmentEngine assignmentEngine = new AssignmentEngineV1();
        assignmentEngine.assign(context);

        return AssignmentResult.from(context);
    }

    public AssignmentResult assignInterviewV2() {

        AssignmentContext context = buildAssignmentContext();

        // 배정 엔진 선택 및 실행
        ScoringModel scoringModel = new ScoringModelV2(ScoringWeight.defaultWeight());

        AssignmentEngine assignmentEngine = new AssignmentEngineV2(scoringModel);
        assignmentEngine.assign(context);

        return AssignmentResult.from(context);
    }

    public AssignmentResult assignInterviewV21() {

        AssignmentContext context = buildAssignmentContext();

        assignmentEngine.assign(context);

        return AssignmentResult.from(context);
    }

    private AssignmentContext buildAssignmentContext(){
        // 배정 대상 지원자 조회
        // submitted == true && passStatus == DOCUMENT_PASSED
        Set<Application> applications = applicationRepository.findInterviewTargets();


        // 고정된 면접 시간 조회
        List<InterviewTime> interviewTimes = interviewTimeRepository.findAllOrderByTime();

        // 가능 시간 맵 구성
        // Application -> 가능한 InterviewTime 집합
        Map<Application, Set<InterviewTime>> availabilityMap = interviewAvailableRepository.buildAvailabilityMap();


        // AssignmentContext 생성
        return new AssignmentContext(applications, interviewTimes, availabilityMap);
    }


}
