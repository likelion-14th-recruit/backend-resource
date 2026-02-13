package org.likelion.recruit.resource.interview.repository;

import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.custom.InterviewTimeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface InterviewTimeRepository extends JpaRepository<InterviewTime, Long>, InterviewTimeRepositoryCustom {
    @Query("select it.id from InterviewTime it where it.date = :date and it.startTime = :startTime")
    Optional<Long> findIdByDateAndStartTime(LocalDate date, LocalTime startTime);

    Optional<InterviewTime> findByDateAndStartTime(LocalDate date, LocalTime startTime);
}
