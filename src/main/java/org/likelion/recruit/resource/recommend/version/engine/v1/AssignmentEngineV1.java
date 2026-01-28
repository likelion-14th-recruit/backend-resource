package org.likelion.recruit.resource.recommend.version.engine.v1;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.AssignmentEngine;

import java.util.ArrayList;
import java.util.List;

public class AssignmentEngineV1 implements AssignmentEngine {

    @Override
    public void assign(AssignmentContext context) {

        for (InterviewTime time : context.getInterviewTimes()) {

            List<Application> candidates =
                    new ArrayList<>(context.getUnAssignedApplications());

            for (Application app : candidates) {
                if (!context.hasCapacity(time)) break;

                if (context.isAvailable(app, time)) {
                    context.assign(app, time);
                }
            }
        }

    }
}

