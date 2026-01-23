package org.likelion.recruit.resource.recommend.version.engine.v3.dto.responseCommon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ScoringWeightDto {

    private final double samePartReward;
    private final double diffPartReward;
    private final double singlePenalty;
    private final double unavailablePenalty; // hard constraint면 그대로 세팅
    private final double timeCompactReward;
    private final double designBackendPenalty;

    public static ScoringWeightDto of(
            double samePartReward,
            double diffPartReward,
            double singlePenalty,
            double unavailablePenalty,
            double timeCompactReward,
            double designBackendPenalty
    ) {
        return ScoringWeightDto.builder()
                .samePartReward(samePartReward)
                .diffPartReward(diffPartReward)
                .singlePenalty(singlePenalty)
                .unavailablePenalty(unavailablePenalty)
                .timeCompactReward(timeCompactReward)
                .designBackendPenalty(designBackendPenalty)
                .build();
    }
}

