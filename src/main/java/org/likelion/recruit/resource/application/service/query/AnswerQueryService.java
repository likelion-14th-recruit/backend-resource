package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.domain.Question.Type;
import org.likelion.recruit.resource.application.dto.result.AnswersRefactorResult;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;
import org.likelion.recruit.resource.application.dto.result.AnswersResult.AnswerInfo;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerQueryService {
    private final AnswerRepository answerRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * 지원서 답변 조회하기
     */

    public AnswersResult getAnswers(Long id, Part part) {

        Type type = changeType(part);
        List<AnswerInfo> answerInfos = answerRepository.findAnswersByApplication(id, type);

        return AnswersResult.of(answerInfos);
    }

    public List<AnswersRefactorResult> getAnswersRefactor(Long id) {
        return answerRepository.getAnswers(id);
    }

    private Type changeType(Part part) {
        if(part.equals(Part.PRODUCT_DESIGN)){
            return Type.PRODUCT_DESIGN;
        }
        return Type.DEVELOPMENT;
    }
}
