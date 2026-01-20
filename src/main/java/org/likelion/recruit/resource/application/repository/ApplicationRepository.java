package org.likelion.recruit.resource.application.repository;

import org.likelion.recruit.resource.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
