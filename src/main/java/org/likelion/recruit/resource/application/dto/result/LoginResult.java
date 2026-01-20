package org.likelion.recruit.resource.application.dto.result;

import lombok.Getter;

@Getter
public class LoginResult {
    private String phoneNumber;
    private Integer passwordLength;

    private LoginResult(String phoneNumber, Integer passwordLength) {
        this.phoneNumber = phoneNumber;
        this.passwordLength = passwordLength;
    }

    public static LoginResult of(String phoneNumber, Integer passwordLength){
        return new  LoginResult(phoneNumber, passwordLength);
    }
}
