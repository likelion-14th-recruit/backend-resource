package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.AcademicStatus;
import org.likelion.recruit.resource.application.domain.Application.PassStatus;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
public class ApplicationSearchResult {
    private String applicationPublicId;
    private String name;
    private String studentNumber;
    private AcademicStatus academicStatus;
    private String phoneNumber;
    private Part part;
    private PassStatus passStatus;

    public ApplicationSearchResult(String applicationPublicId, String name, String studentNumber, AcademicStatus academicStatus, String phoneNumber, Part part, PassStatus passStatus) {
        this.applicationPublicId = applicationPublicId;
        this.name = name;
        this.studentNumber = studentNumber;
        this.academicStatus = academicStatus;
        this.phoneNumber = phoneNumber;
        this.part = part;
        this.passStatus = passStatus;
    }
}
