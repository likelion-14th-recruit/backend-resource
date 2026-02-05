package org.likelion.recruit.resource.recommend.version.engine.v3.service;


import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAIWeightTuningService {

    private final OpenAIClient openAIClient;

    public String recommendNextWeight(WeightTuningRequest req) {

        String system = """
            너는 '면접 시간 배정 ScoringWeight'를 튜닝하는 최적화 조언자다.
            입력으로 주어지는 currentWeight, evaluationResult, objective, searchSpace를 바탕으로
            "다음 실험에 사용할 recommendedWeight"를 제안해야 한다.

            [반드시 지켜야 할 하드 룰]
            1) recommendedWeight.unavailablePenalty는 currentWeight.unavailablePenalty와 반드시 동일해야 한다. (절대 변경 금지)
            2) recommendedWeight의 각 값은 반드시 searchSpace 범위 안에 있어야 한다.
            3) objective.hardConstraint(예: maxUnassignedCount=0)은 절대 깨지면 안 된다.
            4) objective.priority 우선순위를 존중해 트레이드오프를 결정하라.

            [출력 형식]
            - 오직 JSON만 출력하라. (설명 텍스트/마크다운/코드블록 금지)
            - JSON은 아래 스키마와 정확히 일치해야 한다.

            스키마(WeightTuningResponse):
            {
                "recommendedWeight": {
                    "samePartReward": number,
                    "diffPartReward": number,
                    "singlePenalty": number,
                    "unavailablePenalty": number,
                    "timeCompactReward": number,
                    "designBackendPenalty": number
                },
                "analysis": {
                    "observations": [string],
                    "adjustments": [string]
                },
                "expectedEffect": {
                    "unassignedCount": string,
                    "singleInterviewCount": string,
                    "designBackendPairCount": string,
                    "timeCompactScore": string
                }
            }

            [expectedEffect 작성 규칙 - 짧은 한국어]
            - 예: "0 유지", "감소", "증가", "유지"
        """;

        String user = """
        아래는 가중치 튜닝 요청 정보다.
        이 정보를 바탕으로 WeightTuningResponse 형식의 JSON만 출력해라.

        [currentWeight]
        - samePartReward=%s
        - diffPartReward=%s
        - singlePenalty=%s
        - unavailablePenalty=%s
        - timeCompactReward=%s
        - designBackendPenalty=%s

        [evaluationResult]
        - unassignedCount=%d
        - singleInterviewCount=%d
        - designBackendPairCount=%d
        - timeCompactScore=%d
        - totalScore=%s

        [objective]
        - priority=%s
        - hardConstraint=%s

        [searchSpace]
        - samePartReward=[%s, %s]
        - diffPartReward=[%s, %s]
        - singlePenalty=[%s, %s]
        - designBackendPenalty=[%s, %s]
        - timeCompactReward=[%s, %s]
        """.formatted(
                req.getCurrentWeight().samePartReward,
                req.getCurrentWeight().diffPartReward,
                req.getCurrentWeight().singlePenalty,
                req.getCurrentWeight().unavailablePenalty,
                req.getCurrentWeight().timeCompactReward,
                req.getCurrentWeight().designBackendPenalty,

                req.getEvaluationResult().getUnassignedCount(),
                req.getEvaluationResult().getSingleInterviewCount(),
                req.getEvaluationResult().getDesignBackendPairCount(),
                req.getEvaluationResult().getTimeCompactScore(),
                req.getEvaluationResult().getTotalScore(),

                req.getObjective().getPriority(),
                req.getObjective().getHardConstraint(),

                req.getSearchSpace().getSamePartReward().getMin(), req.getSearchSpace().getSamePartReward().getMax(),
                req.getSearchSpace().getDiffPartReward().getMin(), req.getSearchSpace().getDiffPartReward().getMax(),
                req.getSearchSpace().getSinglePenalty().getMin(), req.getSearchSpace().getSinglePenalty().getMax(),
                req.getSearchSpace().getDesignBackendPenalty().getMin(), req.getSearchSpace().getDesignBackendPenalty().getMax(),
                req.getSearchSpace().getTimeCompactReward().getMin(), req.getSearchSpace().getTimeCompactReward().getMax()
        );

        var completion = openAIClient.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_5_2)
                        .addSystemMessage(system)
                        .addUserMessage(user)
                        .build()
        );

        return completion.choices().get(0).message().content()
                .orElseThrow(() -> new IllegalStateException("OpenAI 응답 content가 비었습니다."));
    }
}
