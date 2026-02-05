package org.likelion.recruit.resource.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.PublicIdResult;

@Getter
@Builder
public class PublicIdResponse {
    private String publicId;

    public static PublicIdResponse from(PublicIdResult result){
        return PublicIdResponse.builder()
                .publicId(result.getPublicId())
                .build();
    }
}
