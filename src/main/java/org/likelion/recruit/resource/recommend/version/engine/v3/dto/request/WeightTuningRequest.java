package org.likelion.recruit.resource.recommend.version.engine.v3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.Objective;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.SearchSpace;

@Getter
@AllArgsConstructor
public class WeightTuningRequest {

    private final ScoringWeight currentWeight;
    private final EvaluationResult evaluationResult;
    private final Objective objective;
    private final SearchSpace searchSpace;

}
