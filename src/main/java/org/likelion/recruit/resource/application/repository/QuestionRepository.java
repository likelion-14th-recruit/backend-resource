package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
