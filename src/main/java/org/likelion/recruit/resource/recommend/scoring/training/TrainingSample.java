package org.likelion.recruit.resource.recommend.scoring.training;

import org.likelion.recruit.resource.recommend.scoring.feature.FeatureVector;

public class TrainingSample {

    private final FeatureVector vector;
    private final int label; // 1 or 0

    public TrainingSample(FeatureVector vector, int label) {
        this.vector = vector;
        this.label = label;
    }

    public FeatureVector getVector() {
        return vector;
    }

    public int getLabel() {
        return label;
    }
}
