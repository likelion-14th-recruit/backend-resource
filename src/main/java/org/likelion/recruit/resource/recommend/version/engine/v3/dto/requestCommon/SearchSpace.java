package org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchSpace {
    private final Range samePartReward;
    private final Range diffPartReward;
    private final Range singlePenalty;
    private final Range designBackendPenalty;
    private final Range timeCompactReward;
}
