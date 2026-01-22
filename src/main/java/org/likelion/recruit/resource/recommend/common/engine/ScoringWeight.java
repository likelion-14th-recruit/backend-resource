package org.likelion.recruit.resource.recommend.common.engine;

public class ScoringWeight {

    public final double samePartReward;
    public final double diffPartReward;
    public final double singlePenalty;
    public final double unavailablePenalty;
    public final double timeCompactReward;

    public ScoringWeight(double samePartReward, double diffPartReward, double singlePenalty,
                         double unavailablePenalty, double timeCompactReward) {
        this.samePartReward = samePartReward;
        this.diffPartReward = diffPartReward;
        this.singlePenalty = singlePenalty;
        this.unavailablePenalty = unavailablePenalty;
        this.timeCompactReward = timeCompactReward;
    }

    public static ScoringWeight defaultWeight() {
        return new ScoringWeight(
                10.0,   // same part
                2.0,    // different part
                -3.0,   // single
                -1000.0, // unavailable
                5.0     // 시간 밀집도
        );
    }
}
