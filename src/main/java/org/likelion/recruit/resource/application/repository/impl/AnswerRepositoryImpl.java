package org.likelion.recruit.resource.application.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.QAnswer;
import org.likelion.recruit.resource.application.domain.QQuestion;
import org.likelion.recruit.resource.application.dto.result.AnswersRefactorResult;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;
import org.likelion.recruit.resource.application.repository.custom.AnswerRepositoryCustom;

import java.util.List;

import static org.likelion.recruit.resource.application.domain.QAnswer.answer;
import static org.likelion.recruit.resource.application.domain.QApplication.application;
import static org.likelion.recruit.resource.application.domain.QQuestion.question;

@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AnswersRefactorResult> getAnswers(String publicId){
        return queryFactory.select(Projections.constructor(AnswersRefactorResult.class,
                question.id,
                question.questionNumber,
                answer.content
                ))
                .from(answer)
                .join(answer.question, question)
                .join(answer.application, application)
                .where(application.publicId.eq(publicId))
                .fetch();
    }
}
