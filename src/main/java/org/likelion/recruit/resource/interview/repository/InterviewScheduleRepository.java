package org.likelion.recruit.resource.interview.repository;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule,Long> {
    @Query("SELECT s FROM InterviewSchedule s JOIN FETCH s.interviewTime WHERE s.application = :application")
    Optional<InterviewSchedule> findByApplication(@Param("application") Application application);
}
