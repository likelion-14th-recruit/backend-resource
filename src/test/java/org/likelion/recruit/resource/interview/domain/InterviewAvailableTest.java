package org.likelion.recruit.resource.interview.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InterviewAvailableTest {

    @Test
    void createInterviewAvailable() {
        // interviewTime
        LocalDate todayDate = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        InterviewTime interviewTime = InterviewTime.create(todayDate, todayTime);

        // application
        String name = "김지오";
        String studentNumber = "20201223";
        String phoneNumber = "01012345678";
        String passwordHash = "hashed-password";
        String major = "수학과";
        String doubleMajor = "복수전공";
        Integer semester = 7;
        Application.AcademicStatus status = Application.AcademicStatus.ENROLLED;
        Part part = Part.BACKEND;

        Application application = Application.create(name, studentNumber, phoneNumber,
                passwordHash, major, doubleMajor, semester, status, part);

        InterviewAvailable available = InterviewAvailable.create(interviewTime, application);

        assertThat(available).isNotNull();
        assertThat(available.getInterviewTime().getDate()).isEqualTo(todayDate);
        assertThat(available.getApplication().getName()).isEqualTo(name);

    }
}