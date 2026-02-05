package org.likelion.recruit.resource.application.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String phoneNumber;
    private String password;
}
