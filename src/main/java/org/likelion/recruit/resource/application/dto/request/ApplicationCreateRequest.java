package org.likelion.recruit.resource.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "학번은 필수 입력 값입니다.")
    private String studentNumber;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phoneNumber;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "전공은 필수 입력 값입니다.")
    private String major;


    private String doubleMajor;

    @NotNull(message = "학기는 필수 입력 값입니다.")
    private Integer semester;

    @NotNull(message = "학적 상태는 필수 입력 값입니다.")
    private AcademicStatus academicStatus;

    @NotNull(message = "지원 파트는 필수 입력 값입니다.")
    private Part part;

}
