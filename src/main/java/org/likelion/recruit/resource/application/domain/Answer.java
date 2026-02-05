package org.likelion.recruit.resource.application.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

import static jakarta.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private Answer(String content, Question question, Application application) {
        this.content = content;
        this.question = question;
        this.application = application;
    }

    public static Answer create(String content, Question question, Application application) {
        return new Answer(content, question, application);
    }

    // 편의 메서드

    public void updateContent(String content) {
        this.content = content;
    }

}
