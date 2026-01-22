package org.likelion.recruit.resource.recommend.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@Builder
public class AssignedApplicationResponse {

    private final String publicId;
    private final String name;
    private final Part part;

    private AssignedApplicationResponse(String publicId, String name, Part part) {
        this.publicId = publicId;
        this.name = name;
        this.part = part;
    }

    public static AssignedApplicationResponse from(Application application) {
        return AssignedApplicationResponse.builder()
                .publicId(application.getPublicId())
                .name(application.getName())
                .part(application.getPart())
                .build();
    }
}
