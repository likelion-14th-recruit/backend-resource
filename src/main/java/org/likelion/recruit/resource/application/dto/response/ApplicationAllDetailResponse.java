package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.ApplicationAllDetailResult;
import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;

@Getter
@Builder
public class ApplicationAllDetailResponse {
    private String name;
    private String studentNumber;
    private String part;
    private String phoneNumber;
    private String major;
    private String doubleMajor;
    private String academicStatus;
    private Integer semester;
    private String passStatus;

    public static ApplicationAllDetailResponse from(ApplicationAllDetailResult result) {
        return ApplicationAllDetailResponse.builder()
                .name(result.getName())
                .studentNumber(result.getStudentNumber())
                .part(result.getPart().toString())
                .phoneNumber(result.getPhoneNumber())
                .major(result.getMajor())
                .doubleMajor(result.getDoubleMajor())
                .academicStatus(result.getAcademicStatus().toString())
                .semester(result.getSemester())
                .passStatus(result.getPassStatus().toString())
                .build();
    }
}
