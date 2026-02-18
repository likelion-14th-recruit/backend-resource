package org.likelion.recruit.resource.application.repository;

import jakarta.persistence.LockModeType;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.PassStatus;
import org.likelion.recruit.resource.application.repository.custom.ApplicationRepositoryCustom;
import org.likelion.recruit.resource.common.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long>, ApplicationRepositoryCustom {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndPasswordHash(String phoneNumber, String passwordHash);
    Optional<Application> findByPublicId(String publicId);
    Optional<Application> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndSubmitted(String phoneNumber, boolean submitted);

    @Query("select ap.part from Application ap where ap.publicId = :publicId")
    Optional<Part> findTypeByPublicId(@Param("publicId") String publicId);


    @Query("select ap.id from Application ap where ap.publicId = :publicId")
    Optional<Long> findIdByPublicId(@Param("publicId") String publicId);


    //메시지 전송 관련 메서드
    @Query("select ap.id from Application ap where ap.passStatus = :passStatus")
    List<Long> findIdByPassStatus(@Param("passStatus") PassStatus passStatus);



    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Application a where a.publicId = :publicId")
    Optional<Application> findByPublicIdForUpdate(@Param("publicId") String publicId);


}
