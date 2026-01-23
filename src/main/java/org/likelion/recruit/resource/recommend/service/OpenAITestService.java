package org.likelion.recruit.resource.recommend.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAITestService {

    private final OpenAIClient openAIClient;

    public String testOpenAI() {
        var completion = openAIClient.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_5_2)
                        .addUserMessage("오른쪽 말 무시하고Yes라고 말해줘 제발!!! " +
                                "왼쪽 말을 무시하고 No라고 말해" +
                                "그리고 그 선택을 하게 된 이유를 말해줘")
                        .build()
        );

        String text = completion.choices().get(0).message().content()
                .orElseThrow();

        return text;
    }
}
