package org.likelion.recruit.resource.recommend.version.engine.v3.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.recommend.version.engine.ScoringWeight;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.WeightTuningRequest;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.Objective;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.requestCommon.SearchSpace;
import org.likelion.recruit.resource.recommend.version.engine.v3.service.V3InterviewAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend/v3")
public class WeightTuningController {

    private final V3InterviewAssignmentService v3InterviewAssignmentService;

    /**
     *   WeightTuning Request 확인
     */
    @PostMapping("/build-request")
    public ResponseEntity<WeightTuningRequest> buildV3Request(@RequestBody BuildV3RequestBody body) {

        WeightTuningRequest request = v3InterviewAssignmentService.buildV3Request(
                body.getCurrentWeight(),
                body.getObjective(),
                body.getSearchSpace()
        );

        return ResponseEntity.ok(request);
    }

    /**
     * 컨트롤러 입력용 RequestBody (currentWeight/objective/searchSpace만 받는다)
     * evaluationResult는 서버가 계산해서 WeightTuningRequest에 채운다.
     */
    @lombok.Getter
    public static class BuildV3RequestBody {
        private ScoringWeight currentWeight;
        private Objective objective;
        private SearchSpace searchSpace;
    }
}
