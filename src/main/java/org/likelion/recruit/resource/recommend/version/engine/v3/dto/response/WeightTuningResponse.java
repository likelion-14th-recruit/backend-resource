package org.likelion.recruit.resource.recommend.version.engine.v3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.responseCommon.AnalysisDto;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.responseCommon.ExpectedEffectDto;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.responseCommon.ScoringWeightDto;

@Getter
@AllArgsConstructor
public class WeightTuningResponse {

    private final ScoringWeightDto recommendedWeight;
    private final AnalysisDto analysis;
    private final ExpectedEffectDto expectedEffect;
}
