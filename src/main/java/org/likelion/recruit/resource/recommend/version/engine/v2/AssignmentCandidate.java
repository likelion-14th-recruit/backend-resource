package org.likelion.recruit.resource.recommend.version.engine.v2;

import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.util.List;

@Getter
public class AssignmentCandidate {

    private final InterviewTime time;
    private final List<Application> applications;
    private final double score;

    public AssignmentCandidate(InterviewTime time, List<Application> applications, double score) {
        this.time = time;
        this.applications = applications;
        this.score = score;
    }

}
