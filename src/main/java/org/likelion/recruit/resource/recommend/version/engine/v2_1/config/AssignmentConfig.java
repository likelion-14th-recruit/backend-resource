package org.likelion.recruit.resource.recommend.version.engine.v2_1.config;

import org.likelion.recruit.resource.recommend.version.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.version.engine.ScoringModel;
import org.likelion.recruit.resource.recommend.version.engine.v2.AssignmentEngineV2;
import org.likelion.recruit.resource.recommend.version.engine.v2_1.PartCompatibilityScore;
import org.likelion.recruit.resource.recommend.version.engine.v2_1.SoloPenaltyScore;
import org.likelion.recruit.resource.recommend.version.engine.v2_1.TimeDensityScore;
import org.likelion.recruit.resource.recommend.version.engine.v2_1.TotalScoringModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class AssignmentConfig {

    @Bean
    public ScoringModel scoringModelV21() {
        return new TotalScoringModel(
                List.of(
                        new PartCompatibilityScore(),
                        new SoloPenaltyScore(),
                        new TimeDensityScore()
                ),
                Map.of(
                        PartCompatibilityScore.class, 1.0,
                        SoloPenaltyScore.class, 1.0,
                        TimeDensityScore.class, 1.0
                )
        );
    }

    @Bean
    public AssignmentEngine assignmentEngineV21(
            ScoringModel scoringModelV21
    ) {
        return new AssignmentEngineV2(scoringModelV21);
    }
}

