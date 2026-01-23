package org.likelion.recruit.resource.recommend.version.engine.v3.evaluator;

import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 배정 완료 후, 하루 인터뷰 슬롯들의 "연속성(timeCompactScore)"을 평가하는 전역 평가기
 *
 * 정의(B안):
 * - 같은 날짜에서 "배정된" InterviewTime(assignedApplications.size() > 0)만 모은다.
 * - 시작시간 기준으로 정렬한 뒤, 연속된 타임 묶음(segment) 수를 계산한다.
 * - dayScore = filledCount - segmentCount
 * - totalScore = Σ(dayScore)
 *
 * 전제:
 * - InterviewTime#isImmediatelyBefore(InterviewTime) 는 "고정 슬롯 간격" 기준으로
 *   바로 다음 슬롯인지 판단하는 계약을 가진다.
 */
@Component
public class TimeCompactEvaluator {

    public int calculate(AssignmentContext context) {


        // 1) 날짜별로 "배정된" InterviewTime만 그룹핑
        Map<LocalDate, List<InterviewTime>> timesByDate =
                context.getInterviewTimes().stream()
                        .filter(time -> !context.getAssignedApplications(time).isEmpty())
                        .collect(Collectors.groupingBy(InterviewTime::getDate));

        int totalScore = 0;

        // 2) 날짜별 segment 계산
        for (Map.Entry<LocalDate, List<InterviewTime>> entry : timesByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<InterviewTime> times = entry.getValue();

            // (방어) 배정된 것만 grouping했으므로 일반적으로 비어있지 않음
            if (times == null || times.isEmpty()) {
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
            int dayScore = filledCount - segments;

            // 불변식: filledCount >= segments 이어야 함 (깨지면 segment 판정 로직/데이터가 이상한 것)
            if (dayScore < 0) {
                throw new IllegalStateException(
                        "timeCompactScore invariant broken: date=" + date
                                + ", filled=" + filledCount
                                + ", segments=" + segments
                );
            }

            totalScore += dayScore;
        }

        return totalScore;
    }
}
