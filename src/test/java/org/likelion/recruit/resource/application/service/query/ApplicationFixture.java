package org.likelion.recruit.resource.application.service.query;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.common.domain.Part;
import org.springframework.test.util.ReflectionTestUtils;

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
}
