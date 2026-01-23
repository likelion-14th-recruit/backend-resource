package org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HardConstraint {
    private final int maxUnassignedCount;
}
