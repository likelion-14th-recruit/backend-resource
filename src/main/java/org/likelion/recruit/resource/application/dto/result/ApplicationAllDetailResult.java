package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
public class ApplicationAllDetailResult {


    private String name;
    private String studentNumber;
    private Part part;
    private String phoneNumber;
    private String major;
    private String doubleMajor;
    private String academicStatus;
    private Integer semester;
    private Application.PassStatus passStatus;

    public ApplicationAllDetailResult(String name, String studentNumber, Part part, String phoneNumber,
                                      String major, String doubleMajor, String academicStatus,
                                      Integer semester, Application.PassStatus passStatus) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.part = part;
        this.phoneNumber = phoneNumber;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.academicStatus = academicStatus;
        this.semester = semester;
        this.passStatus = passStatus;
    }
}
