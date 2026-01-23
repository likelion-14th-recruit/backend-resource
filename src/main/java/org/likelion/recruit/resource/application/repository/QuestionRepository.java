package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.repository.custom.QuestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    long countByTypeIn(List<Question.Type> types);
}
