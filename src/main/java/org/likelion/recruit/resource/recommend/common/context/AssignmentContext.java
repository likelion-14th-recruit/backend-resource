package org.likelion.recruit.resource.recommend.common.context;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.interview.domain.InterviewTime;

import java.time.LocalDate;
import java.util.*;

public class AssignmentContext {

    /**
     * 불변
     */
    // 배정 대상 지원자 전체
    private final Set<Application> applications;
    // 면접 슬롯 전체
    private final List<InterviewTime> interviewTimes;
    // 지원자가 가능한 시간
    private final Map<Application, Set<InterviewTime>> availabilityMap;

    /**
     * 가변
     */
    // 면접 시간 배정 상태
    private final Map<InterviewTime, Set<Application>> assignments = new HashMap<>();
    // 배정된 지원자 집합
    private final Set<Application> assignedApplications = new HashSet<>();
    // 미배정된 지원자 집합
    private final Set<Application> unAssignedApplications = new HashSet<>();

    /**
     * 정책
     */
    // 면접 시간 당 가능 인원 수
    private final int capacityPerTime = 2;

    /**
     * 생성 메서드
     */

    public AssignmentContext(
            Set<Application> applications,
            List<InterviewTime> interviewTimes,
            Map<Application, Set<InterviewTime>> availabilityMap
    ) {
        this.applications = applications;
        this.interviewTimes = interviewTimes;
        this.availabilityMap = availabilityMap;

        // 초기 상태는 전원 미배정
        this.unAssignedApplications.addAll(applications);

        // 각 InterviewTime 슬롯 초기화
        for (InterviewTime time : interviewTimes) {
            assignments.put(time, new HashSet<>());
        }
    }

    /**
     * 조회 메서드
     */
    // 지원자가 배정된 지원자 집합에 존재하는지
    public boolean isAssigned(Application application) {
        return assignedApplications.contains(application);
    }

    // 지원자가 특정 면접 시간에 지원 가능한지
    // availabilityMap에 없으면 "불가능"으로 해석
    public boolean isAvailable(Application application, InterviewTime time) {
        return availabilityMap
                .getOrDefault(application, Collections.emptySet())
                .contains(time);
    }

    // 지원 시간이 꽉 차지 않았는지
    public boolean hasCapacity(InterviewTime time) {
        return assignments.get(time).size() < capacityPerTime;
    }

    // 특정 면접 시간에 지원자 조회
    public Set<Application> getAssignedApplications(InterviewTime time) {
        return assignments.get(time);
    }

    // 미배정된 지원자 조회
    public Set<Application> getUnAssignedApplications() {
        return Collections.unmodifiableSet(unAssignedApplications);
    }

    // 이 날짜에 이미 배정된 인터뷰 시간들 조회
    public List<InterviewTime> getAssignedTimesOnDate(LocalDate date) {
        return assignments.entrySet().stream()
                .filter(entry -> entry.getKey().getDate().equals(date))
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty()) // 핵심
                .map(Map.Entry::getKey)
                .toList();
    }
    // 면접 시간 조회
    public List<InterviewTime> getInterviewTimes() {
        return interviewTimes;
    }

    /**
     *  상태 변경 메서드
     **/

    // 특정 지원자를 특정 면접 시간에 등록시킬 수 있는지
    public void assign(Application application, InterviewTime time) {
        if (isAssigned(application)) {
            throw new IllegalStateException("이미 배정된 지원자입니다.");
        }
        if (!hasCapacity(time)) {
            throw new IllegalStateException("해당 면접 시간은 정원이 초과되었습니다.");
        }

        assignments.get(time).add(application);
        assignedApplications.add(application);
        unAssignedApplications.remove(application);
    }

}
