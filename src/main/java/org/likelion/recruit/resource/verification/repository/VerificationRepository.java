package org.likelion.recruit.resource.verification.repository;

import org.likelion.recruit.resource.verification.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification,Long> {
    boolean existsByPhoneNumberAndVerifiedTrue(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Verification> findByPhoneNumber(String phoneNumber);
}
