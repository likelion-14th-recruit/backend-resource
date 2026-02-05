package org.likelion.recruit.resource.application.dto.query;

import lombok.Getter;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
public class PassedMessageTarget {
    private String name;
    private String phoneNumber;
    private Part part;

    public PassedMessageTarget(String name, String phoneNumber, Part part) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.part = part;
    }
}
