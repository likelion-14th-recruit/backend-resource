package org.likelion.recruit.resource.recommend.scoring.service;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.scoring.feature.FeatureExtractor;
import org.likelion.recruit.resource.recommend.scoring.model.ScoringModel;

@RequiredArgsConstructor
public class InterviewScoringService {

    private final ScoringModel scoringModel;

    public double score(Application application, InterviewTime time) {
        return scoringModel.score(
                FeatureExtractor.extract(application, time)
        );
    }
}
