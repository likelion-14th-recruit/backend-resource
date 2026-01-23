package org.likelion.recruit.resource.recommend.version.engine.v3.factory;

import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.EvaluationResult;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.Objective;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.SearchSpace;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.EvaluationResultCalculator;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.TimeCompactEvaluator;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.TotalScoreEvaluator;

/**
 * V3 튜닝 입력(WeightTuningRequest)을 "서버에서" 조립하는 팩토리
 *
 * 전제:
 * - context는 AssignmentEngine(V2.x) 실행 이후 최종 배정(assignments)이 반영된 상태
 */
public class WeightTuningRequestFactory {

    private final TimeCompactEvaluator timeCompactEvaluator;
    private final TotalScoreEvaluator totalScoreEvaluator;
    private final EvaluationResultCalculator evaluationResultCalculator;

    public WeightTuningRequestFactory() {
        this.timeCompactEvaluator = new TimeCompactEvaluator();
        this.totalScoreEvaluator = new TotalScoreEvaluator(timeCompactEvaluator);
        this.evaluationResultCalculator = new EvaluationResultCalculator(timeCompactEvaluator);
    }

    public WeightTuningRequest create(
            AssignmentContext context,
            ScoringWeight currentWeight,
            Objective objective,
            SearchSpace searchSpace
    ) {
        double totalScore = totalScoreEvaluator.calculate(context, currentWeight);
        EvaluationResult evaluationResult = evaluationResultCalculator.calculate(context, totalScore);

        return new WeightTuningRequest(
                currentWeight,
                evaluationResult,
                objective,
                searchSpace
        );
    }
}
