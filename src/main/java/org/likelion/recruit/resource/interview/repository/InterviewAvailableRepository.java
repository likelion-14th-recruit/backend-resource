package org.likelion.recruit.resource.interview.repository;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.likelion.recruit.resource.interview.repository.custom.InterviewAvailableRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InterviewAvailableRepository extends JpaRepository<InterviewAvailable, Long>, InterviewAvailableRepositoryCustom {

    @Query("select count(ia) > 0 from InterviewAvailable ia where ia.interviewTime.id = :interviewTimeId and ia.application.id = :applicationId")
    boolean existsByInterviewTimeAndApplication(@Param("interviewTimeId") Long interviewTimeId, @Param("applicationId") Long applicationId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from InterviewAvailable ia where ia.application.id = :applicationId")
    void deleteByApplicationId(@Param("applicationId") Long applicationId);

    boolean existsByApplication(Application application);

    @EntityGraph(attributePaths = {"interviewTime"})
    List<InterviewAvailable> findAllByApplication(Application application);
}
