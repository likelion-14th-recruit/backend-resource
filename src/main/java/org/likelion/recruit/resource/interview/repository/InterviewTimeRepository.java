package org.likelion.recruit.resource.interview.repository;

import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.custom.InterviewTimeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewTimeRepository extends JpaRepository<InterviewTime, Long>, InterviewTimeRepositoryCustom {
}
