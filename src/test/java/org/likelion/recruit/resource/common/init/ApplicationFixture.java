package org.likelion.recruit.resource.common.init;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public class ApplicationFixture {

    public static Application createApplication(String publicId) {
        Application app = Application.create(
                "성민", "20200129", "01041019429", "hash123",
                "국어국문학과", "융합소프트웨어전공", 7,
                Application.AcademicStatus.ENROLLED, Part.BACKEND
        );
        ReflectionTestUtils.setField(app, "publicId", publicId);
        return app;
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

    public static InterviewScheduleCommand createCommand(String publicId, String place) {
        return InterviewScheduleCommand.builder()
                .applicationPublicId(publicId)
                .date(LocalDate.of(2026, 1, 29))
                .startTime(LocalTime.of(14, 0))
                .place(place)
                .build();
    }

    public static InterviewScheduleCommand createCommand(String publicId, String place, LocalDate date, LocalTime startTime) {
        return InterviewScheduleCommand.builder()
                .applicationPublicId(publicId)
                .date(date)
                .startTime(startTime)
                .place(place)
                .build();
    }
}
