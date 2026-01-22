package org.likelion.recruit.resource.recommend.common.engine.v3.simulation;

import org.likelion.recruit.resource.recommend.common.context.AssignmentContext;
import org.likelion.recruit.resource.recommend.common.engine.v3.cases.SimulationCase;

public interface SimulationRunner {
    AssignmentContext run(SimulationCase simulationCase);
}

