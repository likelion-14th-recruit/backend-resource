package org.likelion.recruit.resource.application.dto.query;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class DocumentPassedMessageTarget {
    private String name;
    private String phoneNumber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String place;

    public DocumentPassedMessageTarget(String name, String phoneNumber, LocalDate date, LocalTime startTime, LocalTime endTime, String place) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
    }
}
