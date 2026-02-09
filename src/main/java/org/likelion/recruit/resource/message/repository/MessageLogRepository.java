package org.likelion.recruit.resource.message.repository;

import org.likelion.recruit.resource.message.domain.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
}
