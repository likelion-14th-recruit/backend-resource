package org.likelion.recruit.resource.recommend.version.engine.v3.dto.responseCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpectedEffectDto {

    private final String unassignedCount;         // "0 유지"
    private final String singleInterviewCount;    // "감소"
    private final String designBackendPairCount;  // "감소"
    private final String timeCompactScore;        // "증가"
}

