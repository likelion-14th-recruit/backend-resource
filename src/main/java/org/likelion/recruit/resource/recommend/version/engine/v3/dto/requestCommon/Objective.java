package org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Objective {
    private final List<ObjectiveKey> priority;
    private final HardConstraint hardConstraint;
}
