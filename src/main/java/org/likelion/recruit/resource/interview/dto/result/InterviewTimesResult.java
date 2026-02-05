package org.likelion.recruit.resource.interview.dto.result;

import lombok.Getter;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeDto;

import java.time.LocalDate;
import java.util.List;

@Getter
public class InterviewTimesResult {

    private LocalDate date;

    private List<InterviewTimeDto> interviewTimes;

    public InterviewTimesResult(LocalDate date, List<InterviewTimeDto> interviewTimes) {
        this.date = date;
        this.interviewTimes = interviewTimes;
    }
}
