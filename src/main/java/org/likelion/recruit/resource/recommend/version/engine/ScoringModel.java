package org.likelion.recruit.resource.recommend.version.engine;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;

public interface ScoringModel {

    double scorePair(Application a, Application b, InterviewTime time, AssignmentContext context);

    double scoreSingle(Application a, InterviewTime time, AssignmentContext context);
}

