package org.likelion.recruit.resource.interview.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.Part;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InterviewScheduleTest {

    @Test
    void createInterviewTime() {
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

        String place = "J201";
        InterviewSchedule interviewSchedule = InterviewSchedule.create(place, application, interviewTime);

        assertThat(interviewSchedule).isNotNull();
        assertThat(interviewSchedule.getPlace()).isEqualTo(place);
        assertThat(interviewSchedule.getApplication().getPart()).isEqualTo(part);
        assertThat(interviewSchedule.getInterviewTime().getStartTime()).isEqualTo(todayTime);
    }

    @Test
    void assignPlace() {
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

        InterviewSchedule interviewSchedule = InterviewSchedule.create(application, interviewTime);

        assertThat(interviewSchedule.getPlace()).isNull();

        interviewSchedule.assignPlace("J201");

        assertThat(interviewSchedule.getPlace()).isEqualTo("J201");

    }
}