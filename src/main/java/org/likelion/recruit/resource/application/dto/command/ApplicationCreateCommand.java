package org.likelion.recruit.resource.application.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.request.ApplicationCreateRequest;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@Builder
public class ApplicationCreateCommand {

    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String password;
    private String major;
    private String doubleMajor;
    private Integer semester;
    private Application.AcademicStatus academicStatus;
    private Part part;

    public static ApplicationCreateCommand from(ApplicationCreateRequest req) {
        return ApplicationCreateCommand.builder()
                .name(req.getName())
                .studentNumber(req.getStudentNumber())
                .phoneNumber(req.getPhoneNumber())
                .password(req.getPassword())
                .major(req.getMajor())
                .doubleMajor(req.getMajor())
                .semester(req.getSemester())
                .academicStatus(req.getAcademicStatus())
                .part(req.getPart())
                .build();
    }
}
