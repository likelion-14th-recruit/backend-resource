package org.likelion.recruit.resource.application.dto.query;

import lombok.Getter;

@Getter
public class RejectedMessageTarget {
    private String name;
    private String phoneNumber;

    public RejectedMessageTarget(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
