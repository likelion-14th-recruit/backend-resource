package org.likelion.recruit.resource.question.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(nullable = false)
    private Integer questionNumber;

    @Column(nullable = false)
    private String content;

    public enum Type {
        DEVELOPMENT,            //백&프
        PRODUCT_DESIGN,         //기디
        COMMON                  //공용질문
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    private Question(Integer questionNumber, String content, Type type) {
        this.questionNumber = questionNumber;
        this.content = content;
        this.type = type;
    }

    public static Question create(Integer questionNumber, String content, Type type) {
        return new Question(questionNumber, content, type);
    }
}
