package org.likelion.recruit.resource.recommend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@NoArgsConstructor
public class UnAssignedApplicationRequest {

    private String publicId;
    private String name;
    private Part part;
}
