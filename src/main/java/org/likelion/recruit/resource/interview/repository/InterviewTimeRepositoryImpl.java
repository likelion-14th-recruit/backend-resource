package org.likelion.recruit.resource.interview.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.interview.domain.QInterviewTime;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeDto;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeQueryDto;

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
}
