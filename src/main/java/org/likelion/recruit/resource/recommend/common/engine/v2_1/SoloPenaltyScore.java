package org.likelion.recruit.resource.recommend.common.engine.v2_1;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;

public class SoloPenaltyScore implements ScoreFactor {

    @Override
    public double scorePair(Application a, Application b, InterviewTime time, AssignmentContext context) {
        return 0.0;
    }

    @Override
    public double scoreSingle(Application a, InterviewTime time, AssignmentContext context) {
        return -1.0;
    }
}
