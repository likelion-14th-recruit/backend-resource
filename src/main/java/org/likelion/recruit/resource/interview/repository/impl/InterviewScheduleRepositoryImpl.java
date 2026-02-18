package org.likelion.recruit.resource.interview.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.query.DocumentPassedMessageTarget;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.likelion.recruit.resource.interview.repository.custom.InterviewScheduleRepositoryCustom;

import java.util.List;

import static org.likelion.recruit.resource.application.domain.QApplication.application;
import static org.likelion.recruit.resource.interview.domain.QInterviewSchedule.interviewSchedule;
import static org.likelion.recruit.resource.interview.domain.QInterviewTime.interviewTime;

@RequiredArgsConstructor
public class InterviewScheduleRepositoryImpl implements InterviewScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DocumentPassedMessageTarget> findDocumentPassedMessageTargets(List<Long> documentPassedIds) {
        return queryFactory.select(Projections.constructor(DocumentPassedMessageTarget.class,
                        application.name,
                        application.phoneNumber,
                        interviewTime.date,
                        interviewTime.startTime,
                        interviewTime.endTime,
                        interviewSchedule.place
                ))
                .from(interviewSchedule)
                .join(interviewSchedule.application, application)
                .join(interviewSchedule.interviewTime, interviewTime)
                .where(
                    application.passStatus.eq(Application.PassStatus.DOCUMENT_PASSED),
                        application.id.in(documentPassedIds),
                        placeValid(),
                        interviewSchedule.interviewTime.isNotNull()

                )
                .fetch();
    }

    private BooleanExpression placeValid() {
        return interviewSchedule.place.isNotNull()
                .and(Expressions.stringTemplate("TRIM({0})", interviewSchedule.place).ne(""));
    }

}
