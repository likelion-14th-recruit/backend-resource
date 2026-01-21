package org.likelion.recruit.resource.application.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.request.ApplicationSearchRequest;
import org.likelion.recruit.resource.common.domain.Part;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ApplicationSearchCommand {
    private Part part;
    private Application.PassStatus passStatus;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;

    public static ApplicationSearchCommand from(ApplicationSearchRequest req){
        return ApplicationSearchCommand.builder()
                .part(req.getPart())
                .passStatus(req.getPassStatus())
                .date(req.getDate())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .name(req.getName())
                .build();
    }
}
