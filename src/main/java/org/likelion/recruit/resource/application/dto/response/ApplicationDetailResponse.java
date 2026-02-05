package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;

@Getter
@Builder
public class ApplicationDetailResponse {
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private Integer passwordLength;
    private String major;
    private String doubleMajor;
    private String academicStatus;
    private Integer semester;
    private String part;

    public static ApplicationDetailResponse from(ApplicationDetailResult result) {
        return ApplicationDetailResponse.builder()
                .name(result.getName())
                .studentNumber(result.getStudentNumber())
                .phoneNumber(result.getPhoneNumber())
                .passwordLength(result.getPasswordLength())
                .major(result.getMajor())
                .doubleMajor(result.getDoubleMajor())
                .academicStatus(result.getAcademicStatus().toString())
                .semester(result.getSemester())
                .part(result.getPart().toString())
                .build();
    }


}
