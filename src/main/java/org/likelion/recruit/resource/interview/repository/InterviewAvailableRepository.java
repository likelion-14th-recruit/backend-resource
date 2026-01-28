package org.likelion.recruit.resource.interview.repository;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.likelion.recruit.resource.interview.repository.custom.InterviewAvailableRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface InterviewAvailableRepository extends JpaRepository<InterviewAvailable, Long>, InterviewAvailableRepositoryCustom {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from InterviewAvailable ia where ia.application.id = :applicationId")
    void deleteByApplicationId(@Param("applicationId") Long applicationId);

    boolean existsByApplication(Application application);

    @Query("select ia from InterviewAvailable ia " +
            "join fetch ia.interviewTime " +
            "where ia.application = :application")
    List<InterviewAvailable> findAllByApplication(@Param("application") Application application);

    @Query("SELECT ia FROM InterviewAvailable ia " +
            "JOIN FETCH ia.interviewTime it " +
            "WHERE ia.application = :application " +
            "AND it.date = :date " +
            "AND it.startTime = :startTime " +
            "AND it.endTime = :endTime")
    Optional<InterviewAvailable> findByApplicationAndDateTime(
            @Param("application") Application application,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
