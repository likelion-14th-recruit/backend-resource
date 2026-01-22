package org.likelion.recruit.resource.application.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.request.PassStatusUpdateRequest;

@Getter
@AllArgsConstructor
public class PassStatusUpdateCommand {

    private String passStatus;

    public static PassStatusUpdateCommand from(PassStatusUpdateRequest request) {
        return new PassStatusUpdateCommand(request.getPassStatus());
    }
}
