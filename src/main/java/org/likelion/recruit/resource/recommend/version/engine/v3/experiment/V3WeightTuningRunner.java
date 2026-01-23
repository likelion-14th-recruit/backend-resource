package org.likelion.recruit.resource.recommend.version.engine.v3.experiment;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.version.engine.ScoringModel;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v2.AssignmentEngineV2;
import org.likelion.recruit.resource.recommend.version.engine.v2.ScoringModelV2;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.EvaluationResult;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.EvaluationService;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.TimeCompactEvaluator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class V3WeightTuningRunner {

    private static final int TRY_COUNT = 500;

    private final ApplicationRepository applicationRepository;
    private final InterviewTimeRepository interviewTimeRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;
    private final EvaluationService evaluationService;

    public V3WeightTuningRunner(
            ApplicationRepository applicationRepository,
            InterviewTimeRepository interviewTimeRepository,
            InterviewAvailableRepository interviewAvailableRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.interviewTimeRepository = interviewTimeRepository;
        this.interviewAvailableRepository = interviewAvailableRepository;

        this.evaluationService =
                new EvaluationService(new TimeCompactEvaluator());
    }

    /**
     * V3 실험 실행
     */
    public void run() {

        for (int i = 0; i < TRY_COUNT; i++) {

            // Context 생성
            AssignmentContext context = buildContext();

            // weight 선택
            ScoringWeight weight = pickWeight(i);

            // 엔진 조립
            ScoringModel scoringModel = new ScoringModelV2(weight);
            AssignmentEngine engine = new AssignmentEngineV2(scoringModel);

            // 배정 실행
            engine.assign(context);

            // 평가
            double totalScore = 0.0; // 지금은 0, 나중에 확장 가능
            EvaluationResult result =
                    evaluationService.evaluate(context, totalScore);

            // 기록 / 비교
            logResult(i, weight, result);
        }
    }

    /**
     * AssignmentContext 생성
     */
    private AssignmentContext buildContext() {

        // 배정 대상 지원자
        Set<Application> applications =
                applicationRepository.findInterviewTargets();

        // 면접 시간
        List<InterviewTime> interviewTimes =
                interviewTimeRepository.findAllOrderByTime();

        // 지원자별 가능 시간 맵
        Map<Application, Set<InterviewTime>> availabilityMap =
                interviewAvailableRepository.buildAvailabilityMap();

        return new AssignmentContext(
                applications,
                interviewTimes,
                availabilityMap
        );
    }

    /**
     * 실험용 weight 선택 로직
     * (지금은 단순히 index 기반, 나중에 AI가 이 역할을 대체)
     */
    private ScoringWeight pickWeight(int iteration) {

        return ScoringWeight.builder()
                .samePartReward(10 + (iteration % 5))
                .diffPartReward(2)
                .singlePenalty(-3 - (iteration % 5))
                .unavailablePenalty(-1000)
                .timeCompactReward(5 + (iteration % 3))
                .designBackendPenalty(-7 - (iteration % 5))
                .build();
    }

    /**
     * 결과 기록 (지금은 콘솔, 나중에 DB/파일 가능)
     */
    private void logResult(
            int iteration,
            ScoringWeight weight,
            EvaluationResult result
    ) {
        System.out.println(
                "[V3] iteration=" + iteration +
                        ", weight=" + weight +
                        ", result=" + result
        );
    }
}
