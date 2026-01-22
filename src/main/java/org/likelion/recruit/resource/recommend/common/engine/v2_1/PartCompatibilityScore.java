package org.likelion.recruit.resource.recommend.common.engine.v2_1;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;

public class PartCompatibilityScore implements ScoreFactor {

    @Override
    public double scorePair(Application a, Application b, InterviewTime time, AssignmentContext context) {
        if (a.getPart() == b.getPart()) {
            return 1.0;
        }
        if (isFrontendBackend(a, b)) {
            return 0.4;
        }
        if (isFrontendDesign(a, b)) {
            return 0.3;
        }
        return -1.0;
    }

    @Override
    public double scoreSingle(Application a, InterviewTime time, AssignmentContext context) {
        return 0.0;
    }

    private boolean isFrontendBackend(Application a, Application b) {
        return (a.getPart() == Part.FRONTEND && b.getPart() == Part.BACKEND)
                || (a.getPart() == Part.BACKEND && b.getPart() == Part.FRONTEND);
    }

    private boolean isFrontendDesign(Application a, Application b) {
        return (a.getPart() == Part.FRONTEND && b.getPart() == Part.PRODUCT_DESIGN)
                || (a.getPart() == Part.PRODUCT_DESIGN && b.getPart() == Part.FRONTEND);
    }
}

