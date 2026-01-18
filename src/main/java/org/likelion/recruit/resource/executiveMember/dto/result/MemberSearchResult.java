package org.likelion.recruit.resource.executiveMember.dto.result;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.executiveMember.domain.Position;

@Getter
public class MemberSearchResult {
    private String imageUrl;
    private String name;
    private Integer cohort;
    private Position position;
    private Part part;

    public MemberSearchResult(String imageUrl, String name, Integer cohort, Position position, Part part) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.cohort = cohort;
        this.position = position;
        this.part = part;
    }
}
