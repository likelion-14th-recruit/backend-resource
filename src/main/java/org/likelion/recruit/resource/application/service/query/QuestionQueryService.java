package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Question.Type;
import org.likelion.recruit.resource.application.dto.query.QuestionCommonDto;
import org.likelion.recruit.resource.application.dto.result.QuestionsResult;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.application.repository.QuestionRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionQueryService {

    private final ApplicationRepository applicationRepository;
    private final QuestionRepository questionRepository;

    public QuestionsResult getQuestions(String publicId) {
        Part part = applicationRepository.findTypeByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        Type type = convertToType(part);
        List<QuestionCommonDto> questions = questionRepository.getQuestions(type);

        return QuestionsResult.from(questions);

    }

    private Type convertToType(Part part) {
        if(part == Part.PRODUCT_DESIGN) {
            return Type.PRODUCT_DESIGN;
        }
        return Type.DEVELOPMENT;
    }
}
