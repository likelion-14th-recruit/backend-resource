package org.likelion.recruit.resource.interview.repository.custom;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.util.Map;
import java.util.Set;

public interface InterviewAvailableRepositoryCustom {
    Map<Application, Set<InterviewTime>> buildAvailabilityMap();
}
