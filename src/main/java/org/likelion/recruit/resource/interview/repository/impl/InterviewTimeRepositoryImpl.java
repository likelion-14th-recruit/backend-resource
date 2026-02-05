package org.likelion.recruit.resource.interview.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeQueryDto;
import org.likelion.recruit.resource.interview.repository.custom.InterviewTimeRepositoryCustom;

import java.util.List;

import static org.likelion.recruit.resource.interview.domain.QInterviewTime.interviewTime;

@RequiredArgsConstructor
public class InterviewTimeRepositoryImpl implements InterviewTimeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<InterviewTimeQueryDto> findAllInterviewTimes(){
        return queryFactory.select(Projections.constructor(
                InterviewTimeQueryDto.class,
                interviewTime.id,
                interviewTime.date,
                interviewTime.startTime,
                interviewTime.endTime))
                .from(interviewTime)
                .fetch();
    }

    @Override
    public List<InterviewTime> findAllOrderByTime(){
        return queryFactory.select(interviewTime)
                .from(interviewTime)
                .orderBy(
                        interviewTime.date.asc(),
                        interviewTime.startTime.asc()
                )
                .fetch();
    }
}
