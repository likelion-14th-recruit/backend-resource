package org.likelion.recruit.resource.recommend.common.engine.v3.cases;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record SimulationCase(

        Set<Application> applications,

        List<InterviewTime> interviewTimes,

        Map<Application, Set<InterviewTime>> availabilityMap

) {}
