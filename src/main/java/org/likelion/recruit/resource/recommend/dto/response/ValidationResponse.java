package org.likelion.recruit.resource.recommend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion.recruit.resource.recommend.dto.result.AvailabilityViolation;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationResponse {
    private boolean valid;
    private int violationCount;
    private List<AvailabilityViolation> violations;

    public static ValidationResponse from(List<AvailabilityViolation> violations) {
        return new ValidationResponse(
                violations.isEmpty(),
                violations.size(),
                violations
        );
    }
}

