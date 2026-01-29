package org.likelion.recruit.resource.interview.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.likelion.recruit.resource.interview.dto.result.InterviewScheduleResult;

@Getter
@AllArgsConstructor
public class InterviewScheduleResponse {

    private String date;
    private String startTime;
    private String endTime;
    private String place;

    public static InterviewScheduleResponse from(
            InterviewScheduleResult result
    ){
        return new InterviewScheduleResponse(
                result.getDate().toString(),
                result.getStartTime().toString(),
                result.getEndTime().toString(),
                result.getPlace()
        );
    }
}
