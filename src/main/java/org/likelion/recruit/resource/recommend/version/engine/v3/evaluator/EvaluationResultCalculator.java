package org.likelion.recruit.resource.recommend.version.engine.v3.evaluator;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.EvaluationResult;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * AssignmentEngine(V2.x) 실행 "후" AssignmentContext(assignments)가 반영된 상태에서
 * 평가 지표(EvaluationResult)를 계산한다.
 *
 * 계산 대상:
 * - unassignedCount: context.getUnAssignedApplications().size()
 * - singleInterviewCount: 배정 인원 1명인 InterviewTime 슬롯 수
 * - designBackendPairCount: 배정 인원 2명이며 (디자인, 백엔드) 조합인 슬롯 수
 * - timeCompactScore: TimeCompactEvaluator(B안) 결과
 *
 * totalScore는 전역 점수 평가기(예: TotalScoreEvaluator)에서 계산한 값을 외부에서 주입받는다.
 * (지금 단계에서는 0.0 넣고 시작해도 OK)
 */
@Component
public class EvaluationResultCalculator {

    private final TimeCompactEvaluator timeCompactEvaluator;

    public EvaluationResultCalculator(TimeCompactEvaluator timeCompactEvaluator) {
        this.timeCompactEvaluator = timeCompactEvaluator;
    }

    /**
     * @param context    최종 배정이 반영된 AssignmentContext
     * @param totalScore 전역 점수 평가 결과(없으면 0.0으로 시작 가능)
     */
    public EvaluationResult calculate(AssignmentContext context, double totalScore) {
        int unassignedCount = context.getUnAssignedApplications().size();
        int singleInterviewCount = calculateSingleInterviewCount(context);
        int designBackendPairCount = calculateDesignBackendPairCount(context);
        int timeCompactScore = timeCompactEvaluator.calculate(context);

        return new EvaluationResult(
                unassignedCount,
                singleInterviewCount,
                designBackendPairCount,
                timeCompactScore,
                totalScore
        );
    }

    private int calculateSingleInterviewCount(AssignmentContext context) {
        int count = 0;
        for (InterviewTime time : context.getInterviewTimes()) {
            Set<Application> assigned = context.getAssignedApplications(time);
            if (assigned != null && assigned.size() == 1) {
                count++;
            }
        }
        return count;
    }

    private int calculateDesignBackendPairCount(AssignmentContext context) {
        int count = 0;

        for (InterviewTime time : context.getInterviewTimes()) {
            Set<Application> assigned = context.getAssignedApplications(time);
            if (assigned == null || assigned.size() != 2) {
                continue;
            }

            Iterator<Application> it = assigned.iterator();
            Application a = it.next();
            Application b = it.next();

            if (isDesignBackendPair(a, b)) {
                count++;
            }
        }

        return count;
    }

    private boolean isDesignBackendPair(Application a, Application b) {
        Part p1 = a.getPart();
        Part p2 = b.getPart();

        return (p1 == Part.PRODUCT_DESIGN && p2 == Part.BACKEND)
                || (p1 == Part.BACKEND && p2 == Part.PRODUCT_DESIGN);
    }
}
