package org.likelion.recruit.resource.executiveMember.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;

@Getter
@Builder
public class MemberSearchResponse {
    private String imageUrl;
    private String name;
    private Integer cohort;
    private String position;
    private String part;

    public static MemberSearchResponse from(MemberSearchResult result){
        return MemberSearchResponse.builder()
                .imageUrl(result.getImageUrl())
                .name(result.getName())
                .cohort(result.getCohort())
                .position(result.getPosition().toString())
                .part(result.getPart().toString())
                .build();
    }
}
