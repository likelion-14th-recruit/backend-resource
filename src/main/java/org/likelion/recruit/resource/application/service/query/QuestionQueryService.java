package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.result.QuestionCommonResult;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.application.repository.QuestionRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionQueryService {

    private final ApplicationRepository applicationRepository;
    private final QuestionRepository questionRepository;

    public QuestionCommonResult getQuestions(String publicId) {
        Part part = applicationRepository.findTypeByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));



    }
}
