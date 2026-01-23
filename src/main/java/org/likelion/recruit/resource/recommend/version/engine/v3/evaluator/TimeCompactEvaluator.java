package org.likelion.recruit.resource.recommend.version.engine.v3.evaluator;

import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 배정 완료 후, 하루 인터뷰 슬롯들의 "연속성"을 평가하는 전역 평가기
 */
public class TimeCompactEvaluator {

    /**
     * timeCompactScore 계산
     *
     * 정의:
     * - 같은 날짜의 배정된 InterviewTime을 정렬
     * - 연속된 타임 묶음(segment) 수를 계산
     * - score = filledCount - segmentCount
     */
    public int calculate(AssignmentContext context) {

        // 1. 날짜별로 "배정된" InterviewTime만 그룹핑
        Map<LocalDate, List<InterviewTime>> timesByDate =
                context.getInterviewTimes().stream()
                        .filter(time -> !context.getAssignedApplications(time).isEmpty())
                        .collect(Collectors.groupingBy(InterviewTime::getDate));

        int totalScore = 0;

        // 2. 날짜별로 segment 계산
        for (List<InterviewTime> times : timesByDate.values()) {

            if (times.isEmpty()) {
                continue;
            }

            // 시작 시간 기준 정렬
            times.sort(Comparator.comparing(InterviewTime::getStartTime));

            int segments = 1;

            for (int i = 1; i < times.size(); i++) {
                InterviewTime prev = times.get(i - 1);
                InterviewTime curr = times.get(i);

                // 연속되지 않으면 새로운 segment
                if (!prev.isImmediatelyBefore(curr)) {
                    segments++;
                }
            }

            int filledCount = times.size();

            // 하루 점수 = filled - segments
            int dayScore = filledCount - segments;

            // 음수 방지 (이론상 0 이상이지만 안전장치)
            totalScore += Math.max(dayScore, 0);
        }

        return totalScore;
    }
}
