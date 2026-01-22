package org.likelion.recruit.resource.recommend.common.engine.v2_1;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.common.engine.ScoringModel;

import java.util.List;
import java.util.Map;

public class TotalScoringModel implements ScoringModel {

    private final List<ScoreFactor> factors;
    private final Map<Class<? extends ScoreFactor>, Double> weights;

    public TotalScoringModel(
            List<ScoreFactor> factors,
            Map<Class<? extends ScoreFactor>, Double> weights
    ) {
        this.factors = factors;
        this.weights = weights;
    }

    @Override
    public double scorePair(
            Application a,
            Application b,
            InterviewTime time,
            AssignmentContext context
    ) {
        return factors.stream()
                .mapToDouble(f ->
                        f.scorePair(a, b, time, context)
                                * weights.getOrDefault(f.getClass(), 1.0)
                )
                .sum();
    }

    @Override
    public double scoreSingle(
            Application a,
            InterviewTime time,
            AssignmentContext context
    ) {
        return factors.stream()
                .mapToDouble(f ->
                        f.scoreSingle(a, time, context)
                                * weights.getOrDefault(f.getClass(), 1.0)
                )
                .sum();
    }
}
