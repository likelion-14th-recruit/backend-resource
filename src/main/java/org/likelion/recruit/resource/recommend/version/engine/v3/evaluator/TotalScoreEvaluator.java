package org.likelion.recruit.resource.recommend.version.engine.v3.evaluator;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * 배정 완료 후, "전역 품질 점수(totalScore)"를 계산하는 평가기
 *
 * 점수 정의(MVP, V3용):
 * 1) 슬롯 단위 점수
 *  - 2명 배정:
 *      + (같은 파트면 samePartReward, 다르면 diffPartReward)
 *      + (디자인-백엔드 조합이면 designBackendPenalty 추가)
 *      + 각 지원자가 그 시간에 불가능이면 unavailablePenalty 추가
 *  - 1명 배정:
 *      + singlePenalty
 *      + 그 지원자가 불가능이면 unavailablePenalty 추가
 *  - 0명 배정:
 *      + 0점 (필요하면 나중에 emptySlotReward 같은 항목으로 확장)
 *
 * 2) 전역 연속성 점수
 *  - timeCompactScore(B안) * timeCompactReward
 *
 * 주의:
 * - unavailablePenalty는 하드 제약이지만, "측정"은 가능해야 하므로 실제로 점수에 반영한다.
 * - 이 totalScore는 '튜닝 방향성'을 위한 전역 스칼라 지표다(절대적인 정답 점수라기보다 일관성이 중요).
 */
@Component
public class TotalScoreEvaluator {

    private final TimeCompactEvaluator timeCompactEvaluator;

    public TotalScoreEvaluator(TimeCompactEvaluator timeCompactEvaluator) {
        this.timeCompactEvaluator = timeCompactEvaluator;
    }

    public double calculate(AssignmentContext context, ScoringWeight weight) {
        double score = 0.0;

        for (InterviewTime time : context.getInterviewTimes()) {
            Set<Application> assigned = context.getAssignedApplications(time);
            if (assigned == null || assigned.isEmpty()) {
                continue;
            }

            if (assigned.size() == 1) {
                Application a = assigned.iterator().next();

                // 단독 슬롯 페널티
                score += weight.singlePenalty;

                // 가능 시간 위반 페널티
                if (!context.isAvailable(a, time)) {
                    score += weight.unavailablePenalty;
                }
                continue;
            }

            if (assigned.size() == 2) {
                Iterator<Application> it = assigned.iterator();
                Application a = it.next();
                Application b = it.next();

                // 페어 점수(같은 파트/다른 파트)
                if (a.getPart() == b.getPart()) {
                    score += weight.samePartReward;
                } else {
                    score += weight.diffPartReward;
                }

                // 디자인-백엔드 조합 추가 페널티
                if (isDesignBackendPair(a, b)) {
                    score += weight.designBackendPenalty;
                }

                // 가능 시간 위반 페널티 (각 지원자별)
                if (!context.isAvailable(a, time)) {
                    score += weight.unavailablePenalty;
                }
                if (!context.isAvailable(b, time)) {
                    score += weight.unavailablePenalty;
                }
                continue;
            }

            // capacityPerTime=2 전제라면 여기로 올 일 없음. 방어적으로 처리.
            // (혹시 정책 변경/버그로 3명 이상이 들어가면, 단순히 2명만 평가하지 않고 모두 평가)
            for (Application a : assigned) {
                // 인원이 초과된 슬롯은 운영적으로 문제가 있는 상태라 강한 페널티를 주고 싶다면 여기서 추가 가능
                if (!context.isAvailable(a, time)) {
                    score += weight.unavailablePenalty;
                }
            }
        }

        // 전역 연속성 점수 반영
        int timeCompactScore = timeCompactEvaluator.calculate(context);
        score += (timeCompactScore * weight.timeCompactReward);

        return score;
    }

    private boolean isDesignBackendPair(Application a, Application b) {
        Part p1 = a.getPart();
        Part p2 = b.getPart();

        return (p1 == Part.PRODUCT_DESIGN && p2 == Part.BACKEND)
                || (p1 == Part.BACKEND && p2 == Part.PRODUCT_DESIGN);
    }
}
