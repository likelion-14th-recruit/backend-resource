package org.likelion.recruit.resource.executiveMember.dto.request;

import lombok.Data;
import lombok.Getter;
import org.likelion.recruit.resource.common.domain.Part;

@Data
public class MemberSearchRequest {
    private Part part;
}
