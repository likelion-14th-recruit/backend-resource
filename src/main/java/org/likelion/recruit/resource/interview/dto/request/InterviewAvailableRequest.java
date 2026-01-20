package org.likelion.recruit.resource.interview.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class InterviewAvailableRequest {
    private List<Long> interviewTimeIds;
}
