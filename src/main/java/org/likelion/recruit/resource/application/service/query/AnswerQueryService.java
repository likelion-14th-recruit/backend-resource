package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.result.AnswersResult;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
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
    public AnswersResult getAnswers(String publicId){
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        List<Answer> answers = answerRepository.findAllByApplication(application);

        List<AnswersResult.AnswerInfo> answerInfos = answerRepository.findAllByApplication(application)
                .stream()
                .map(AnswersResult.AnswerInfo::from)
                .toList();

        return AnswersResult.of(application.getPublicId(), answerInfos);
    }
}
