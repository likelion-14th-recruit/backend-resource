package org.likelion.recruit.resource.application.dto.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicIdResult {
    private String publicId;

    public static PublicIdResult from(String publicId){
        return PublicIdResult.builder()
                .publicId(publicId)
                .build();
    }
}
