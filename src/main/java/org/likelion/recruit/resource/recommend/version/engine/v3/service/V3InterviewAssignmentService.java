package org.likelion.recruit.resource.recommend.version.engine.v3.service;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v3.assembler.WeightTuningRequestAssembler;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.Objective;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.SearchSpace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class V3InterviewAssignmentService {

    private final AssignmentEngine assignmentEngine; // V2.1 bean
    private final WeightTuningRequestAssembler requestAssembler;
    private final ApplicationRepository applicationRepository;
    private final InterviewTimeRepository interviewTimeRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;

//    public WeightTuningRequest buildV3Request(ScoringWeight currentWeight, Objective objective, SearchSpace searchSpace) {
//        AssignmentContext context = buildAssignmentContext();
//
//        assignmentEngine.assign(context);
//
//        return requestAssembler.assemble(context, currentWeight, objective, searchSpace);
//    }

    public WeightTuningRequest buildV3Request(ScoringWeight currentWeight, Objective objective, SearchSpace searchSpace) {
        AssignmentContext context = buildAssignmentContext();

        assignmentEngine.assign(context);

        int assignedApps = context.getAssignedApplications().size();
        int unassignedApps = context.getUnAssignedApplications().size();

        long nullSlots = context.getInterviewTimes().stream()
                .filter(t -> context.getAssignedApplications(t) == null)
                .count();

        long filledSlots = context.getInterviewTimes().stream()
                .filter(t -> {
                    Set<Application> s = context.getAssignedApplications(t);
                    return s != null && !s.isEmpty();
                })
                .count();

        System.out.println("assignedApps=" + assignedApps);
        System.out.println("unassignedApps=" + unassignedApps);
        System.out.println("nullSlots=" + nullSlots);
        System.out.println("filledSlots=" + filledSlots);

        return requestAssembler.assemble(context, currentWeight, objective, searchSpace);
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
