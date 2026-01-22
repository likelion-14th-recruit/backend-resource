package org.likelion.recruit.resource.recommend.common.engine;

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
        return new ScoringWeight(
                10.0,   // 같은 파트
                2.0,    // 다른 파트
                -3.0,   // 혼자 면접
                -1000.0, // 존재하지 않는 시간대
                5.0,     // 시간 밀집도
                -7.0     // 디자인-백엔드 페널티
        );
    }
}
