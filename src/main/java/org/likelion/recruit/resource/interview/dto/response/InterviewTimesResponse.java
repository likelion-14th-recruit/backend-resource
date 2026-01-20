package org.likelion.recruit.resource.interview.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeDto;
import org.likelion.recruit.resource.interview.dto.result.InterviewTimesResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class InterviewTimesResponse {

    private LocalDate date;

    private DayOfWeek dayOfWeek;

    private List<InterviewTimeDto> interviewTimes;

    public static InterviewTimesResponse from(InterviewTimesResult result){
        return InterviewTimesResponse.builder()
                .date(result.getDate())
                .dayOfWeek(result.getDate().getDayOfWeek())
                .interviewTimes(result.getInterviewTimes())
                .build();
    }
}
