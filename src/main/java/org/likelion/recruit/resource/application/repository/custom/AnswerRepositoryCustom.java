package org.likelion.recruit.resource.application.repository.custom;

import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.domain.Question.Type;
import org.likelion.recruit.resource.application.dto.result.AnswersRefactorResult;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;
import org.likelion.recruit.resource.common.domain.Part;

import java.util.List;

public interface AnswerRepositoryCustom {
    List<AnswersRefactorResult> getAnswers(Long id);
    List<AnswersResult.AnswerInfo> findAnswersByApplication(Long id, Type type);
}
