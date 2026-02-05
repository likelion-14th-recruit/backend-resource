package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application.AcademicStatus;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
public class ApplicationDetailResult {
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private Integer passwordLength;
    private String major;
    private String doubleMajor;
    private AcademicStatus academicStatus;
    private Integer semester;
    private Part part;

    public ApplicationDetailResult(String name, String studentNumber, String phoneNumber,
                                   Integer passwordLength, String major, String doubleMajor,
                                   AcademicStatus academicStatus, Integer semester, Part part) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.passwordLength = passwordLength;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.academicStatus = academicStatus;
        this.semester = semester;
        this.part = part;
    }
}
