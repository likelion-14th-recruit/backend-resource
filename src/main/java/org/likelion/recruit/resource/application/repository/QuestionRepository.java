package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.repository.custom.QuestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    long countByType(Question.Type type);
}
