package org.likelion.recruit.resource.application.repository.custom;

import org.likelion.recruit.resource.application.dto.result.AnswersRefactorResult;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;

import java.util.List;

public interface AnswerRepositoryCustom {
    List<AnswersRefactorResult> getAnswers(Long id);
}
