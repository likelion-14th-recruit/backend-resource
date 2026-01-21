package org.likelion.recruit.resource.recommend.scoring.feature;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.time.temporal.ChronoUnit;

public class FeatureExtractor {

    public static FeatureVector extract(Application application, InterviewTime time) {
        FeatureVector vector = new FeatureVector();

        int hour = time.getStartTime().getHour();

        vector.put(InterviewFeature.IS_MORNING, hour < 12 ? 1.0 : 0.0);
        vector.put(InterviewFeature.IS_AFTERNOON, hour >= 12 ? 1.0 : 0.0);
        vector.put(InterviewFeature.DAY_OF_WEEK,
                time.getDate().getDayOfWeek().getValue());

        long daysFromSubmit = ChronoUnit.DAYS.between(
                application.getSubmittedAt().toLocalDate(),
                time.getDate()
        );

        vector.put(InterviewFeature.DAYS_FROM_SUBMIT, (double) daysFromSubmit);

        return vector;
    }
}

