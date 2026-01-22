package org.likelion.recruit.resource.recommend.common.engine.v2;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.common.engine.ScoringModel;
import org.likelion.recruit.resource.recommend.common.engine.ScoringWeight;

import java.time.Duration;
import java.util.List;

@RequiredArgsConstructor
public class ScoringModelV2 implements ScoringModel {

    private final ScoringWeight weight;

    @Override
    public double scorePair(Application a, Application b, InterviewTime time, AssignmentContext context) {
        double score = 0.0;

        // 가능 시간 위반
        if (!context.isAvailable(a, time) || !context.isAvailable(b, time)) {
            score += weight.unavailablePenalty;
        }

        // Part 조합 점수
        if (a.getPart() == b.getPart()) {
            score += weight.samePartReward;
        }
        else if (isDesignBackend(a, b)) {
            score += weight.designBackendPenalty;
        }
        else {
            score += weight.diffPartReward;
        }
        score += timeCompactnessScore(time, context);

        return score;
    }

    @Override
    public double scoreSingle(Application a, InterviewTime time, AssignmentContext context) {
        double score = 0.0;

        if (!context.isAvailable(a, time)) {
            score += weight.unavailablePenalty;
        }

        score += weight.singlePenalty;
        score += timeCompactnessScore(time, context);

        return score;
    }

    private double timeCompactnessScore(InterviewTime candidate, AssignmentContext context) {

        List<InterviewTime> assignedTimes = context.getAssignedTimesOnDate(candidate.getDate());

        if (assignedTimes.isEmpty()) {
            return 0.0;
        }

        final long SLOT_GAP_MINUTES = 30; // 20분 면접 + 10분 휴식

        return assignedTimes.stream()
                .mapToDouble(t -> {
                    long diff = Math.abs(Duration.between(
                            t.getStartTime(),
                            candidate.getStartTime()
                    ).toMinutes());

                    if (diff == SLOT_GAP_MINUTES) return weight.timeCompactReward;          // 바로 다음 슬롯
                    if (diff == SLOT_GAP_MINUTES * 2) return weight.timeCompactReward / 2; // 한 칸 띄움
                    return 0.0;
                })
                .max()
                .orElse(0.0);
    }


    private boolean isDesignBackend(Application a, Application b) {
        return (a.getPart() == Part.PRODUCT_DESIGN && b.getPart() == Part.BACKEND)
                || (a.getPart() == Part.BACKEND && b.getPart() == Part.PRODUCT_DESIGN);
    }

}

