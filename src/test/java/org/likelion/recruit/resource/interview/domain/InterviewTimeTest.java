package org.likelion.recruit.resource.interview.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;


class InterviewTimeTest {

    @Test
    void createInterviewTime() {
        LocalDate todayDate = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        InterviewTime interviewTime = InterviewTime.create(todayDate, todayTime);

        Assertions.assertThat(interviewTime.getDate()).isEqualTo(todayDate);
        Assertions.assertThat(interviewTime.getStartTime()).isEqualTo(todayTime);
        Assertions.assertThat(interviewTime.getEndTime()).isEqualTo(todayTime.plusMinutes(20));

    }
}