package org.likelion.recruit.resource.recommend.version.engine.v3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EvaluationResult {
    // 인터뷰 시간을 배정받지 못한 지원자 수
    // AssignmentContext에서 계산
    private final int unassignedCount;

    // 혼자 면접을 본 인터뷰 슬롯 수
    // InterviewTime -> assignedApplications.size() == 1
    private final int singleInterviewCount;

    // 디자인-백엔드 묶인 인터뷰 슬롯 수
    // InterviewTime 내 파트 조합
    private final int designBackendPairCount;

    // 인터뷰 시간들이 얼마나 연속적으로 배치됐는지에 대한 점수
    // 전역 평가 로직
    private final int timeCompactScore;

    // ScoringModel로 계산된 점수
    // TotalScoringModel 결과
    private final double totalScore;

}
