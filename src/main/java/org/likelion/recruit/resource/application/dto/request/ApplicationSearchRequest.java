package org.likelion.recruit.resource.application.dto.request;


import lombok.Data;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.PassStatus;
import org.likelion.recruit.resource.common.domain.Part;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ApplicationSearchRequest {
    private Part part;
    private PassStatus passStatus;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
}
