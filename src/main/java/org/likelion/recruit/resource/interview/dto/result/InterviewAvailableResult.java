package org.likelion.recruit.resource.interview.dto.result;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InterviewAvailableResult {
    private List<Long> interviewTimeIds;
}