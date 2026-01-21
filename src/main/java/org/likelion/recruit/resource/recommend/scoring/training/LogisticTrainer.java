package org.likelion.recruit.resource.recommend.scoring.training;

import org.likelion.recruit.resource.recommend.scoring.feature.InterviewFeature;
import org.likelion.recruit.resource.recommend.scoring.model.ScoringWeight;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LogisticTrainer {

    private final double learningRate = 0.01;
    private final int epochs = 1000;

    public ScoringWeight train(List<TrainingSample> samples) {

        Map<InterviewFeature, Double> weights =
                new EnumMap<>(InterviewFeature.class);

        for (InterviewFeature feature : InterviewFeature.values()) {
            weights.put(feature, 0.0);
        }

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (TrainingSample sample : samples) {
                double predicted = predict(sample, weights);
                double error = sample.getLabel() - predicted;

                for (InterviewFeature feature : InterviewFeature.values()) {
                    double oldWeight = weights.get(feature);
                    double gradient = error * sample.getVector().get(feature);
                    weights.put(feature, oldWeight + learningRate * gradient);
                }
            }
        }

        ScoringWeight result = new ScoringWeight();
        weights.forEach(result::put);
        return result;
    }

    private double predict(TrainingSample sample,
                           Map<InterviewFeature, Double> weights) {

        double sum = 0.0;
        for (InterviewFeature feature : InterviewFeature.values()) {
            sum += weights.get(feature) * sample.getVector().get(feature);
        }

        return 1.0 / (1.0 + Math.exp(-sum));
    }
}