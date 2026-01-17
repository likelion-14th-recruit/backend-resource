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
public class InterviewAvailable extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_available_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "interview_time_id", nullable = false)
    private InterviewTime interviewTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private InterviewAvailable(InterviewTime interviewTime, Application application) {
        this.interviewTime = interviewTime;
        this.application = application;
    }

    public static InterviewAvailable create(InterviewTime interviewTime, Application application) {
        return new InterviewAvailable(interviewTime, application);
    }
}
