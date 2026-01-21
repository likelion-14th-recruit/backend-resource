package org.likelion.recruit.resource.recommend.scoring.feature;

import java.util.EnumMap;
import java.util.Map;

public class FeatureVector {

    private final Map<InterviewFeature, Double> values =
            new EnumMap<>(InterviewFeature.class);

    public void put(InterviewFeature feature, double value) {
        values.put(feature, value);
    }

    public double get(InterviewFeature feature) {
        return values.getOrDefault(feature, 0.0);
    }

    public Map<InterviewFeature, Double> asMap() {
        return values;
    }
}

