package org.likelion.recruit.resource.interview.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.result.InterviewAvailableDetailResult;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class InterviewAvailableDetailResponse {

    private List<DateGroup> interviewAvailableTimes;

    @Getter
    @AllArgsConstructor
    public static class DateGroup {
        private String date;
        private DayOfWeek dayOfWeek;
        private List<TimeItem> times;
    }

    @Getter
    @AllArgsConstructor
    public static class TimeItem {
        private Long interviewTimeId;
        private String startTime;
        private String endTime;
    }

    public static InterviewAvailableDetailResponse from(
            InterviewAvailableDetailResult result
    ) {
        return from(result.getInterviewTimes());
    }

    private static InterviewAvailableDetailResponse from(
            List<InterviewTime> interviewTimes
    ) {

        Map<LocalDate, List<InterviewTime>> grouped =
                interviewTimes.stream()
                        .collect(Collectors.groupingBy(
                                InterviewTime::getDate,
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

        List<DateGroup> groups =
                grouped.entrySet().stream()
                        .map(entry -> {
                            LocalDate date = entry.getKey();
                            return new DateGroup(
                                    date.toString(),
                                    date.getDayOfWeek(),
                                    entry.getValue().stream()
                                            .map(time -> new TimeItem(
                                                    time.getId(),
                                                    time.getStartTime().toString(),
                                                    time.getEndTime().toString()
                                            ))
                                            .toList()
                            );
                        })
                        .toList();

        return new InterviewAvailableDetailResponse(groups);
    }
}