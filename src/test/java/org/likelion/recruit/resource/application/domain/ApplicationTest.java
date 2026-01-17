package org.likelion.recruit.resource.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Application.AcademicStatus;
import org.likelion.recruit.resource.common.domain.Part;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    @Test
    void createApplication() {
        String name = "김지오";
        String studentNumber = "20201223";
        String phoneNumber = "01012345678";
        String passwordHash = "hashed-password";
        String major = "수학과";
        String doubleMajor = "복수전공";
        Integer semester = 7;
        AcademicStatus status = AcademicStatus.ENROLLED;
        Part part = Part.BACKEND;

        Application application = Application.create(name, studentNumber, phoneNumber,
                passwordHash, major, doubleMajor, semester, status, part);

        assertThat(application.getName()).isEqualTo(name);
        assertThat(application.getStudentNumber()).isEqualTo(studentNumber);
        assertThat(application.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(application.getPasswordHash()).isEqualTo(passwordHash);
        assertThat(application.getMajor()).isEqualTo(major);
        assertThat(application.getDoubleMajor()).isEqualTo(doubleMajor);
        assertThat(application.getSemester()).isEqualTo(semester);

        assertThat(application.isSubmitted()).isFalse();
        assertThat(application.getSubmittedAt()).isNull();

        assertThat(application.getAcademicStatus()).isEqualTo(status);
        assertThat(application.getPart()).isEqualTo(part);
        assertThat(application.getPassStatus()).isEqualTo(Application.PassStatus.PENDING);

        // publicId 검증
        assertThat(application.getPublicId()).startsWith("app-");
        assertThat(application.getPublicId()).hasSize(40);
    }

    @Test
    void submitApplication() {
        String name = "김지오";
        String studentNumber = "20201223";
        String phoneNumber = "01012345678";
        String passwordHash = "hashed-password";
        String major = "수학과";
        String doubleMajor = "복수전공";
        Integer semester = 7;
        AcademicStatus status = AcademicStatus.ENROLLED;
        Part part = Part.BACKEND;

        Application application = Application.create(name, studentNumber, phoneNumber,
                passwordHash, major, doubleMajor, semester, status, part);
        application.submit();

        assertThat(application.isSubmitted()).isTrue();
        assertThat(application.getSubmittedAt()).isNotNull();
    }

}