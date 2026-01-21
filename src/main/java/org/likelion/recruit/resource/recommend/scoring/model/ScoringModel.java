package org.likelion.recruit.resource.recommend.scoring.model;

import org.likelion.recruit.resource.recommend.scoring.feature.FeatureVector;
import org.likelion.recruit.resource.recommend.scoring.feature.InterviewFeature;

public class ScoringModel {

    private final ScoringWeight weight;

    public ScoringModel(ScoringWeight weight) {
        this.weight = weight;
    }

    public double score(FeatureVector vector) {
        double sum = 0.0;

        for (InterviewFeature feature : InterviewFeature.values()) {
            sum += weight.get(feature) * vector.get(feature);
        }

        return sigmoid(sum);
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
}