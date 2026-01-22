package org.likelion.recruit.resource.interview.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.custom.InterviewAvailableRepositoryCustom;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.likelion.recruit.resource.application.domain.QApplication.application;
import static org.likelion.recruit.resource.interview.domain.QInterviewAvailable.interviewAvailable;

@RequiredArgsConstructor
public class InterviewAvailableRepositoryImpl implements InterviewAvailableRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Application, Set<InterviewTime>> buildAvailabilityMap() {

        List<Tuple> mainQuery = queryFactory
                .select(interviewAvailable.application,
                        interviewAvailable.interviewTime)
                .from(interviewAvailable)
                .fetch();

        Map<Application, Set<InterviewTime>> availabilityMap = new HashMap<>();

        for (Tuple tuple : mainQuery) {
            Application application = tuple.get(interviewAvailable.application);
            InterviewTime time = tuple.get(interviewAvailable.interviewTime);

            availabilityMap
                    .computeIfAbsent(application, a -> new HashSet<>())
                    .add(time);
        }

        return availabilityMap;
    }

    @Override
    public boolean existsAvailable(String publicId, Long interviewTimeId) {
        return queryFactory
                .selectOne()
                .from(interviewAvailable)
                .join(interviewAvailable.application, application)
                .where(
                        application.publicId.eq(publicId),
                        interviewAvailable.interviewTime.id.eq(interviewTimeId)
                )
                .fetchFirst() != null;
    }

}
