package org.likelion.recruit.resource.recommend.scoring.model;

import org.likelion.recruit.resource.recommend.scoring.feature.InterviewFeature;

import java.util.EnumMap;
import java.util.Map;

public class ScoringWeight {

    private final Map<InterviewFeature, Double> weights =
            new EnumMap<>(InterviewFeature.class);

    public void put(InterviewFeature feature, double weight) {
        weights.put(feature, weight);
    }

    public double get(InterviewFeature feature) {
        return weights.getOrDefault(feature, 0.0);
    }
}
