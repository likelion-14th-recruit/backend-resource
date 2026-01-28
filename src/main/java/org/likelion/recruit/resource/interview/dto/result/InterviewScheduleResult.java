package org.likelion.recruit.resource.interview.dto.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewScheduleResult {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String place;

    private InterviewScheduleResult(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            String place
    ) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
    }

    public static InterviewScheduleResult from(InterviewSchedule schedule) {
        return new InterviewScheduleResult(
                schedule.getInterviewTime().getDate(),
                schedule.getInterviewTime().getStartTime(),
                schedule.getInterviewTime().getEndTime(),
                schedule.getPlace()
        );
    }
}
