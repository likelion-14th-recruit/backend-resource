package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.LoginResult;

@Getter
@Builder
public class LoginResponse {
    private String phoneNumber;
    private Integer passwordLength;

    public static LoginResponse from(LoginResult result){
        return LoginResponse.builder()
                .phoneNumber(result.getPhoneNumber())
                .passwordLength(result.getPasswordLength())
                .build();
    }

}
