package org.likelion.recruit.resource.recommend.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class AvailabilityViolation {

    private final String applicationPublicId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
}
