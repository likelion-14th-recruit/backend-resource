package org.likelion.recruit.resource.executiveMember.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.executiveMember.dto.request.MemberSearchRequest;

@Getter
@Builder
public class MemberSearchCommand {
    private Part part;

    public static MemberSearchCommand from(MemberSearchRequest req){
        return MemberSearchCommand.builder()
                .part(req.getPart())
                .build();
    }
}
