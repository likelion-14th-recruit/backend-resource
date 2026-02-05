package org.likelion.recruit.resource.interview.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InterviewTime extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_time_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private InterviewTime(LocalDate date, LocalTime startTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(20);
    }

    public static InterviewTime create(LocalDate date, LocalTime startTime) {
        return new InterviewTime(date, startTime);
    }

    public boolean isImmediatelyBefore(InterviewTime other) {
        return this.getDate().equals(other.getDate())
                && this.getEndTime().plusMinutes(5).equals(other.getStartTime());
    }
}
