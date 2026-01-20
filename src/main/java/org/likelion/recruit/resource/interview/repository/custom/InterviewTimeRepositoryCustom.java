package org.likelion.recruit.resource.interview.repository.custom;

import org.likelion.recruit.resource.interview.dto.query.InterviewTimeQueryDto;

import java.util.List;

public interface InterviewTimeRepositoryCustom {

    List<InterviewTimeQueryDto> findAllInterviewTimes();
}
