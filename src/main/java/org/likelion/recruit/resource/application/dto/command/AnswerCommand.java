package org.likelion.recruit.resource.application.dto.command;

import lombok.Getter;

@Getter
public class AnswerCommand {
    private Long questionId;
    private String content;
}
