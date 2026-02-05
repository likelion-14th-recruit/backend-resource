package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;

@Getter
public class LoginResult {
    private String applicationPublicId;
    private String phoneNumber;
    private Integer passwordLength;

    private LoginResult(String applicationPublicId, String phoneNumber, Integer passwordLength) {
        this.applicationPublicId = applicationPublicId;
        this.phoneNumber = phoneNumber;
        this.passwordLength = passwordLength;
    }

    public static LoginResult of(String applicationPublicId, String phoneNumber, Integer passwordLength){
        return new  LoginResult(applicationPublicId, phoneNumber, passwordLength);
    }
}
