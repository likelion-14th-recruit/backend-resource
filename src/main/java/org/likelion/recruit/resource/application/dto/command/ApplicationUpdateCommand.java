package org.likelion.recruit.resource.application.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.request.ApplicationUpdateRequest;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@Builder
public class ApplicationUpdateCommand {
    private String name;
    private String studentNumber;
    private String major;
    private String doubleMajor;
    private Application.AcademicStatus academicStatus;
    private Integer semester;
    private Part part;

    public static ApplicationUpdateCommand from(ApplicationUpdateRequest req) {
        return ApplicationUpdateCommand.builder()
                .name(req.getName())
                .studentNumber(req.getStudentNumber())
                .major(req.getMajor())
                .doubleMajor(req.getDoubleMajor())
                .academicStatus(req.getAcademicStatus())
                .semester(req.getSemester())
                .part(req.getPart())
                .build();
    }
}
