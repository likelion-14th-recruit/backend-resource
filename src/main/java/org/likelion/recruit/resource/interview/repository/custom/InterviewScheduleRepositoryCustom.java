package org.likelion.recruit.resource.interview.repository.custom;

import org.likelion.recruit.resource.application.dto.query.DocumentPassedMessageTarget;

import java.util.List;

public interface InterviewScheduleRepositoryCustom {
    List<DocumentPassedMessageTarget> findDocumentPassedMessageTargets();
}
