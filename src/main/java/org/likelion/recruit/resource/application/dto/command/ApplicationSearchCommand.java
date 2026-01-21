package org.likelion.recruit.resource.application.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.request.ApplicationSearchRequest;
import org.likelion.recruit.resource.common.domain.Part;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class ApplicationSearchCommand {
    private Part part;
    private Application.PassStatus passStatus;
    private List<LocalDate> dates;
    private LocalTime startTime;
    private String search;

    public static ApplicationSearchCommand from(ApplicationSearchRequest req){
        return ApplicationSearchCommand.builder()
                .part(req.getPart())
                .passStatus(req.getPassStatus())
                .dates(req.getDates())
                .startTime(req.getStartTime())
                .search(req.getSearch())
                .build();
    }
}
