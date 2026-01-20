package org.likelion.recruit.resource.application.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.dto.command.AnswerCommand;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.application.repository.QuestionRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerCommandService {

    private final ApplicationRepository applicationRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    /**
     * 최적화 리팩토링 필요
     */
    public void createAnswers(String publicId, List<AnswerCommand> answers) {
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        for (AnswerCommand answerCommand : answers) {
            Question question = questionRepository.findById(answerCommand.getQuestionId())
                            .orElseThrow(() -> new BusinessException(ErrorCode.QUESTION_NOT_EXISTS));

            Optional<Answer> existingAnswer =
                    answerRepository.findByApplicationAndQuestion(application, question);
            // 답변이 이미 존재
            if(existingAnswer.isPresent()) {
                existingAnswer.get().updateContent(answerCommand.getContent());
                continue;
            }
            Answer newAnswer = Answer.create(answerCommand.getContent(), question, application);
            answerRepository.save(newAnswer);
        }

    }
}
