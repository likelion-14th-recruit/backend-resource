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
import org.likelion.recruit.resource.common.resolver.EntityReferenceResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerCommandService {

    private final ApplicationRepository applicationRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final EntityReferenceResolver referenceResolver;

    /**
     * 최적화 리팩토링 필요
     */
    public void createAnswers(Long applicationId, List<AnswerCommand> answers) {

        Application applicationRef = referenceResolver.application(applicationId);

        List<Long> questionIds = answers.stream()
                .map(AnswerCommand::getQuestionId)
                .distinct()
                .toList();

        List<Question> questions = questionRepository.findAllById(questionIds);

        // questions 개수와 questionIds 개수와 다르면 에외
        if (questions.size() != questionIds.size()) {
            throw new BusinessException(ErrorCode.QUESTION_NOT_EXISTS);
        }

        // map<questionId, question> 생성
        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        // 존재하는 answer 확인
        List<Answer> existingAnswers =
                answerRepository.findAllByApplicationId(applicationId);

        // map<QuestionId, answer> 생성, 이미 question을 불러온 상태라 영속성 컨텍스트에 담겨있음
        Map<Long, Answer> answerMap = existingAnswers.stream()
                .collect(Collectors.toMap(answer -> answer.getQuestion().getId(), Function.identity()));

        for (AnswerCommand command : answers) {
            Answer existing = answerMap.get(command.getQuestionId());

            // 이미 답변 존재
            if (existing != null) {
                existing.updateContent(command.getContent());
                continue;
            }

            Answer newAnswer = Answer.create(command.getContent(),
                    questionMap.get(command.getQuestionId()), applicationRef);

            answerRepository.save(newAnswer);
        }
    }
}
