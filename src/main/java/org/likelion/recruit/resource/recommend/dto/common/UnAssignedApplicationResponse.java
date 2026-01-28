package org.likelion.recruit.resource.recommend.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@Builder
public class UnAssignedApplicationResponse {

    private final String publicId;
    private final String name;
    private final Part part;

    private UnAssignedApplicationResponse(String publicId, String name, Part part) {
        this.publicId = publicId;
        this.name = name;
        this.part = part;
    }

    public static UnAssignedApplicationResponse from(Application application) {
        return UnAssignedApplicationResponse.builder()
                .publicId(application.getPublicId())
                .name(application.getName())
                .part(application.getPart())
                .build();
    }
}
