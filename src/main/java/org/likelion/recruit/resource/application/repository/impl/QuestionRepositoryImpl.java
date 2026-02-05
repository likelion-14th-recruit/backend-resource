package org.likelion.recruit.resource.application.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.QQuestion;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.domain.Question.Type;
import org.likelion.recruit.resource.application.dto.query.QuestionCommonDto;
import org.likelion.recruit.resource.application.repository.custom.QuestionRepositoryCustom;

import java.util.List;

import static org.likelion.recruit.resource.application.domain.QQuestion.question;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QuestionCommonDto> getQuestions(Type type) {
        return queryFactory.select(Projections.constructor(QuestionCommonDto.class,
                question.id,
                question.questionNumber,
                question.content)
        )
                .from(question)
                .where(
                        question.type.eq(Type.COMMON)
                                .or(question.type.eq(type))
                )
                .orderBy(question.questionNumber.asc())
                .fetch();
    }
}
