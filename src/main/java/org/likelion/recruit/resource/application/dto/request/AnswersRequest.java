package org.likelion.recruit.resource.application.dto.request;

import lombok.Data;
import org.likelion.recruit.resource.application.dto.command.AnswerCommand;

import java.util.List;

@Data
public class AnswersRequest {

    private List<AnswerCommand> answers;

}
