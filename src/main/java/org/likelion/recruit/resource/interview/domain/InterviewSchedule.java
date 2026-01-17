package org.likelion.recruit.resource.interview.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

import static jakarta.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InterviewSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_schedule_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interview_time_id", nullable = false)
    private InterviewTime interviewTime;

    private InterviewSchedule(Application application, InterviewTime interviewTime) {
        this.application = application;
        this.interviewTime = interviewTime;
    }

    public static InterviewSchedule create(Application application, InterviewTime interviewTime) {
        return new InterviewSchedule(application, interviewTime);
    }
}
