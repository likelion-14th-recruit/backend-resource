package org.likelion.recruit.resource.recommend.version.engine;

import lombok.Builder;

@Builder
public class ScoringWeight {

    public final double samePartReward;
    public final double diffPartReward;
    public final double singlePenalty;
    public final double unavailablePenalty;
    public final double timeCompactReward;
    public final double designBackendPenalty;

    public ScoringWeight(double samePartReward, double diffPartReward, double singlePenalty,
                         double unavailablePenalty, double timeCompactReward, double designBackendPenalty) {
        this.samePartReward = samePartReward;
        this.diffPartReward = diffPartReward;
        this.singlePenalty = singlePenalty;
        this.unavailablePenalty = unavailablePenalty;
        this.timeCompactReward = timeCompactReward;
        this.designBackendPenalty = designBackendPenalty;
    }

    public static ScoringWeight defaultWeight() {
        return ScoringWeight.builder()
                .samePartReward(11.0)  // 같은 파트
                .diffPartReward(3.0)   // 다른 파트
                .singlePenalty(-8.0)   // 혼자 면접 페널티
                .unavailablePenalty(-1000.0)    // 가능 시간대 면접 아닌 경우
                .timeCompactReward(7.0)         // 면접 시간끼리 붙어있는 정도
                .designBackendPenalty(-4.0)     // 디자인-백엔드 페널티
                .build();
    }
}
