package org.likelion.recruit.resource.recommend.version.engine.v3.dto.responseCommon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AnalysisDto {

    private final List<String> observations;
    private final List<String> adjustments;
}

