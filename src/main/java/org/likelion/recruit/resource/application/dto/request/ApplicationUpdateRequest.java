package org.likelion.recruit.resource.application.dto.request;

import lombok.Data;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.AcademicStatus;
import org.likelion.recruit.resource.common.domain.Part;

@Data
public class ApplicationUpdateRequest {
    private String name;
    private String studentNumber;
    private String major;
    private String doubleMajor;
    private AcademicStatus academicStatus;
    private Integer semester;
    private Part part;
}
