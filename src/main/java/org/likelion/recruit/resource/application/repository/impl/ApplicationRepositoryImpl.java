package org.likelion.recruit.resource.application.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.PassStatus;
import org.likelion.recruit.resource.application.domain.QApplication;
import org.likelion.recruit.resource.application.dto.command.ApplicationSearchCommand;
import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;
import org.likelion.recruit.resource.application.dto.result.ApplicationSearchResult;
import org.likelion.recruit.resource.application.repository.custom.ApplicationRepositoryCustom;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.QInterviewAvailable;
import org.likelion.recruit.resource.interview.domain.QInterviewTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.likelion.recruit.resource.application.domain.QApplication.application;
import static org.likelion.recruit.resource.interview.domain.QInterviewAvailable.interviewAvailable;
import static org.likelion.recruit.resource.interview.domain.QInterviewTime.interviewTime;

@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ApplicationDetailResult getDetail(String publicId, Integer passwordLength) {
        return queryFactory.select(Projections.constructor(ApplicationDetailResult.class,
                        application.name,
                        application.studentNumber,
                        application.phoneNumber, Expressions.constant(passwordLength),
                        application.major,
                        application.doubleMajor,
                        application.academicStatus,
                        application.semester,
                        application.part))
                .from(application)
                .where(application.publicId.eq(publicId))
                .fetchOne();
    }

    @Override
    public Page<ApplicationSearchResult> searchApplications(ApplicationSearchCommand command, Pageable pageable) {
        List<ApplicationSearchResult> mainQuery = queryFactory.select(Projections.constructor(
                        ApplicationSearchResult.class,
                        application.publicId,
                        application.name,
                        application.studentNumber,
                        application.phoneNumber,
                        application.part,
                        application.passStatus
                ))
                .from(application)
                .where(
                        application.submitted.eq(true),
                        partEq(command.getPart()),
                        passStatusEq(command.getPassStatus()),
                        nameOrPhoneEndsWith(command.getSearch()),
                        interviewAvailableExists(command)
                )
                .orderBy(
                        application.submittedAt.asc(),
                        application.id.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery =
                queryFactory
                        .select(application.count())
                        .from(application)
                        .where(
                                application.submitted.eq(true),
                                partEq(command.getPart()),
                                passStatusEq(command.getPassStatus()),
                                nameOrPhoneEndsWith(command.getSearch()),
                                interviewAvailableExists(command)
                        );

        return PageableExecutionUtils.getPage(mainQuery, pageable, countQuery::fetchOne);
    }

    @Override
    public Set<Application> findInterviewTargets() {
        return new HashSet<>(queryFactory.select(application)
                .from(application)
                .where(
                        application.submitted.eq(true),
                        application.passStatus.eq(PassStatus.DOCUMENT_PASSED)
                )
                .fetch());
    }

    private BooleanExpression partEq(Part part) {
        return part == null ? null : application.part.eq(part);
    }

    private BooleanExpression passStatusEq(PassStatus passStatus) {
        return passStatus == null ? null : application.passStatus.eq(passStatus);
    }

    private BooleanExpression dateIn(List<LocalDate> dates) {
        if (dates == null || dates.isEmpty()) {
            return null;
        }
        return interviewTime.date.in(dates);
    }

    private BooleanExpression startAt(LocalTime time) {
        if (time == null) {
            return null;
        }
        return interviewTime.startTime.eq(time);
    }

    private BooleanExpression nameOrPhoneEndsWith(String search) {
        if (search == null) return null;
        return application.name.endsWith(search)
                .or(application.phoneNumber.endsWith(search));
    }

    private BooleanExpression interviewAvailableExists(ApplicationSearchCommand command) {
        if ((command.getDates() == null || command.getDates().isEmpty())
                && command.getStartTime() == null) {
            return null;
        }

        return JPAExpressions
                .selectOne()
                .from(interviewAvailable)
                .join(interviewAvailable.interviewTime, interviewTime)
                .where(
                        interviewAvailable.application.eq(application),
                        dateIn(command.getDates()),
                        startAt(command.getStartTime())
                )
                .exists();
    }
}


