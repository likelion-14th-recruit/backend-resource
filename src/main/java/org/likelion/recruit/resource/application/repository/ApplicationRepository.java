package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.custom.ApplicationRepositoryCustom;
import org.likelion.recruit.resource.common.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long>, ApplicationRepositoryCustom {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndPasswordHash(String phoneNumber, String passwordHash);
    Optional<Application> findByPublicId(String publicId);
    Optional<Application> findByPhoneNumber(String phoneNumber);

    @Query("select ap.part from Application ap where ap.publicId = :publicId")
    Optional<Part> findTypeByPublicId(@Param("publicId") String publicId);


    @Query("select ap.id from Application ap where ap.publicId = :publicId")
    Optional<Long> findIdByPublicId(@Param("publicId") String publicId);




}
