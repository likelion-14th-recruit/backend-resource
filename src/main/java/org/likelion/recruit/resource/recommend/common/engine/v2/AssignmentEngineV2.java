package org.likelion.recruit.resource.recommend.common.engine.v2;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.common.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.common.engine.ScoringModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class AssignmentEngineV2 implements AssignmentEngine {

    private final ScoringModel scoringModel;

    @Override
    public void assign(AssignmentContext context) {

        for (InterviewTime time : context.getInterviewTimes()) {

            while (context.hasCapacity(time)) {

                List<AssignmentCandidate> candidates =
                        generateCandidates(time, context);

                if (candidates.isEmpty()) break;

                AssignmentCandidate best =
                        pickBestCandidate(candidates);

                apply(best, context);
            }
        }
    }

    private List<AssignmentCandidate> generateCandidates(InterviewTime time, AssignmentContext context) {
        List<AssignmentCandidate> result = new ArrayList<>();
        List<Application> unAssigned =
                new ArrayList<>(context.getUnAssignedApplications());

        for (int i = 0; i < unAssigned.size(); i++) {
            for (int j = i + 1; j < unAssigned.size(); j++) {

                Application a = unAssigned.get(i);
                Application b = unAssigned.get(j);

                if (!context.isAvailable(a, time)) continue;
                if (!context.isAvailable(b, time)) continue;
                if (!context.hasCapacity(time)) continue;

                double score =
                        scoringModel.scorePair(a, b, time, context);

                result.add(new AssignmentCandidate(
                        time,
                        List.of(a, b),
                        score
                ));
            }
        }

        boolean hasPairCandidate = !result.isEmpty();

        if (!hasPairCandidate) {
            for (Application a : unAssigned) {

                if (!context.isAvailable(a, time)) continue;

                double score =
                        scoringModel.scoreSingle(a, time, context);

                result.add(new AssignmentCandidate(
                        time,
                        List.of(a),
                        score
                ));
            }
        }

        return result;
    }

    private AssignmentCandidate pickBestCandidate(List<AssignmentCandidate> candidates) {
        return candidates.stream()
                .max(Comparator.comparingDouble(AssignmentCandidate::getScore))
                .orElseThrow();
    }

    private void apply(AssignmentCandidate candidate, AssignmentContext context) {
        for (Application app : candidate.getApplications()) {
            context.assign(app, candidate.getTime());
        }
    }
}
