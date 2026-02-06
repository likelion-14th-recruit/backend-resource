package org.likelion.recruit.resource.interview.repository.custom;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterviewAvailableRepositoryCustom {
    Map<Application, Set<InterviewTime>> buildAvailabilityMap();
    boolean existsAvailable(String publicId, Long interviewTimeId);
    List<Long> findInterviewAvailableIdsByApplicationId(Long applicationId);
}
