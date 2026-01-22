package org.likelion.recruit.resource.recommend.common.engine.v2_1;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;

public class TimeDensityScore implements ScoreFactor {

    @Override
    public double scorePair(Application a, Application b, InterviewTime time, AssignmentContext context) {
        return isAdjacent(time, context) ? 0.5 : 0.0;
    }

    @Override
    public double scoreSingle(Application a, InterviewTime time, AssignmentContext context) {
        return isAdjacent(time, context) ? 0.2 : -0.5;
    }

    private boolean isAdjacent(InterviewTime candidate, AssignmentContext context) {
        return context.getAssignedTimesOnDate(candidate.getDate()).stream()
                .anyMatch(t ->
                        t.isImmediatelyBefore(candidate)
                                || candidate.isImmediatelyBefore(t)
                );
    }
}

