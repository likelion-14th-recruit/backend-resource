package org.likelion.recruit.resource.application.repository.custom;

import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.domain.Question.Type;
import org.likelion.recruit.resource.application.dto.query.QuestionCommonDto;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<QuestionCommonDto> getQuestions(Type type);
}
