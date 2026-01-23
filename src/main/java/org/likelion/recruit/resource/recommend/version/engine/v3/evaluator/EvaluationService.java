package org.likelion.recruit.resource.recommend.version.engine.v3.evaluator;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.recommend.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.EvaluationResult;

import java.util.List;
import java.util.Set;

public class EvaluationService {

    private final TimeCompactEvaluator timeCompactEvaluator;

    public EvaluationService(TimeCompactEvaluator timeCompactEvaluator) {
        this.timeCompactEvaluator = timeCompactEvaluator;
    }

    public EvaluationResult evaluate(AssignmentContext context, double totalScore) {

        /* 미배정 지원자 수 (Hard Constraint 검증용) */
        int unassignedCount = context.getUnAssignedApplications().size();

        /* 단독 면접 슬롯 수 */
        int singleInterviewCount =
                (int) context.getInterviewTimes().stream()
                        .filter(time -> context.getAssignedApplications(time).size() == 1)
                        .count();

        /*  디자인–백엔드 페어 수 (정확한 정책 반영) */
        int designBackendPairCount =
                (int) context.getInterviewTimes().stream()
                        .filter(time -> context.getAssignedApplications(time).size() == 2)
                        .filter(time -> isDesignBackendPair(
                                context.getAssignedApplications(time)
                        ))
                        .count();

        /*  하루 인터뷰 시간 연속성 점수 (Step 3에서 구현한 전역 평가) */
        int timeCompactScore = timeCompactEvaluator.calculate(context);

        return new EvaluationResult(unassignedCount, singleInterviewCount,
                designBackendPairCount, timeCompactScore, totalScore);
    }

    /**
     * DESIGN - BACKEND 조합인지 정확히 판별
     */
    private boolean isDesignBackendPair(Set<Application> applications) {
        if (applications.size() != 2) return false;

        List<Part> parts = applications.stream()
                .map(Application::getPart)
                .toList();

        return parts.contains(Part.PRODUCT_DESIGN) && parts.contains(Part.BACKEND);
    }
}
