package org.likelion.recruit.resource.common.init;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public class ApplicationFixture {

    public static Application createApplication(String publicId) {
        Application app = Application.create(
                "김도영", "20220129", "01094294101", "hash123",
                "국어국문학과", "융합소프트웨어전공", 5,
                Application.AcademicStatus.ENROLLED, Part.BACKEND
        );
        ReflectionTestUtils.setField(app, "publicId", publicId);
        return app;
    }

    public static Question createQuestion() {
        return Question.create(1, "지원 동기", Question.Type.COMMON);
    }

    public static Question createQuestion(Long id) {
        Question question = Question.create(1, "지원 동기", Question.Type.COMMON);
        ReflectionTestUtils.setField(question, "id", id);
        return question;
    }

    public static InterviewTime createInterviewTime(Long id) {
        InterviewTime time = InterviewTime.create(
                LocalDate.of(2026, 1, 1),
                LocalTime.of(10, 0)
        );
        ReflectionTestUtils.setField(time, "id", id);
        return time;
    }

    public static InterviewAvailable createAvailable(Application app, InterviewTime time) {
        InterviewAvailable available = InterviewAvailable.create(time, app);
        ReflectionTestUtils.setField(available, "id", 1L);
        return available;
    }
}
