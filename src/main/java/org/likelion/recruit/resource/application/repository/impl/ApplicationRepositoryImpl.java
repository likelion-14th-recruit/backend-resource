package org.likelion.recruit.resource.application.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.QApplication;
import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;
import org.likelion.recruit.resource.application.repository.custom.ApplicationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import static org.likelion.recruit.resource.application.domain.QApplication.application;

@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ApplicationDetailResult getDetail(String publicId, Integer passwordLength){
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
}
