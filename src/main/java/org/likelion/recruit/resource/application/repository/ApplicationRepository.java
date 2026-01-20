package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select ap.part from Application ap where ap.publicId = :publicId")
    Optional<Part> findTypeByPublicId(@Param("publicId") String publicId);


}
