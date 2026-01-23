package org.likelion.recruit.resource.recommend.version.engine.v3.assembler;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.EvaluationResult;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.Objective;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.SearchSpace;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.EvaluationResultCalculator;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.TimeCompactEvaluator;
import org.likelion.recruit.resource.recommend.version.engine.v3.evaluator.TotalScoreEvaluator;
import org.springframework.stereotype.Component;

/**
 * V3: OpenAI에 보낼 WeightTuningRequest를 "서버에서" 조립하는 역할
 *
 * 전제:
 * - context는 AssignmentEngine(V2.1) 실행 이후, 최종 배정(assignments)이 반영된 상태여야 한다.
 */
@Component
@RequiredArgsConstructor
public class WeightTuningRequestAssembler {

    private final TimeCompactEvaluator timeCompactEvaluator;
    private final TotalScoreEvaluator totalScoreEvaluator;
    private final EvaluationResultCalculator evaluationResultCalculator;

    /**
     * 평가값(evaluationResult)을 계산한 뒤 request를 조립한다.
     */
    public WeightTuningRequest assemble(
            AssignmentContext context,
            ScoringWeight currentWeight,
            Objective objective,
            SearchSpace searchSpace
    ) {
        // 1) totalScore 계산
        double totalScore = totalScoreEvaluator.calculate(context, currentWeight);

        // 2) evaluationResult 계산 (unassignedCount 포함)
        EvaluationResult evaluationResult = evaluationResultCalculator.calculate(context, totalScore);

        // 3) request 조립
        return new WeightTuningRequest(
                currentWeight,
                evaluationResult,
                objective,
                searchSpace
        );
    }
}

