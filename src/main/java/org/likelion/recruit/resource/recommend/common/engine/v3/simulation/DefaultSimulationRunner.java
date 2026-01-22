package org.likelion.recruit.resource.recommend.common.engine.v3.simulation;

import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.common.engine.AssignmentEngine;
import org.likelion.recruit.resource.recommend.common.engine.ScoringModel;
import org.likelion.recruit.resource.recommend.common.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.common.engine.v2.AssignmentEngineV2;
import org.likelion.recruit.resource.recommend.common.engine.v2.ScoringModelV2;
import org.likelion.recruit.resource.recommend.common.engine.v3.cases.SimulationCase;

/**
 * V3 시뮬레이션에서
 * V2 AssignmentEngine을 그대로 생성하여 실행하는 Runner
 */
public class DefaultSimulationRunner implements SimulationRunner {

    @Override
    public AssignmentContext run(SimulationCase simulationCase) {

        // 1. Context 생성
        AssignmentContext context = new AssignmentContext(
                simulationCase.applications(),
                simulationCase.interviewTimes(),
                simulationCase.availabilityMap()
        );

        // 2. V2 ScoringModel + Weight
        ScoringModel scoringModel =
                new ScoringModelV2(ScoringWeight.defaultWeight());

        // 3. V2 AssignmentEngine 생성
        AssignmentEngine assignmentEngine =
                new AssignmentEngineV2(scoringModel);

        // 4. 실행
        assignmentEngine.assign(context);

        return context;
    }
}

