package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Answer;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByApplicationAndQuestion(Application application, Question question);

    @Query("select a from Answer a " +
            "join fetch a.question " +
            "where a.application = :application")
    List<Answer> findAllByApplicationWithQuestion(@Param("application") Application application);

    @Query("select count(a) from Answer a where a.application = :application " +
            "and a.question.type in :types")
    long countByApplicationAndQuestionTypeIn(@Param("application") Application application,
                                             @Param("types") List<Question.Type> types);
}
