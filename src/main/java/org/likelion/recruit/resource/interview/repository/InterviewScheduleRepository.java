package org.likelion.recruit.resource.interview.repository;


import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

    Optional<InterviewSchedule> findByApplication(Application application);
}
