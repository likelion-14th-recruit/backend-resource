package org.likelion.recruit.resource.verification.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.verification.dto.request.VerifyPhoneRequest;

@Getter
@Builder
public class VerifyPhoneCommand {
    private String phoneNumber;

    public static VerifyPhoneCommand from(VerifyPhoneRequest request) {
        return VerifyPhoneCommand.builder()
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
