package org.likelion.recruit.resource.verification.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.verification.dto.request.VerifyConfirmRequest;

@Getter
@Builder
public class VerifyConfirmCommand {
    private String phoneNumber;
    private Integer code;

    public static VerifyConfirmCommand from(VerifyConfirmRequest req){
        return VerifyConfirmCommand.builder()
                .phoneNumber(req.getPhoneNumber())
                .code(req.getCode())
                .build();
    }
}
