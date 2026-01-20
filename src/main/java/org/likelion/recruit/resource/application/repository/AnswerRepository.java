package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByApplicationAndQuestion(Application application, Question question);
}
