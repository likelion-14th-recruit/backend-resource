package org.likelion.recruit.resource.application.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.AcademicStatus;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@Setter
public class ApplicationCreateRequest {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    private String studentNumber;
    private String phoneNumber;
    private String password;
    private String major;
    private String doubleMajor;
    private Integer semester;
    private AcademicStatus academicStatus;
    private Part part;

}
