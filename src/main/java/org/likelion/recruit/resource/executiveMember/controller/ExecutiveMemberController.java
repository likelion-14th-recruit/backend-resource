package org.likelion.recruit.resource.executiveMember.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.executiveMember.dto.command.MemberSearchCommand;
import org.likelion.recruit.resource.executiveMember.dto.request.MemberSearchRequest;
import org.likelion.recruit.resource.executiveMember.dto.response.MemberSearchResponse;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;
import org.likelion.recruit.resource.executiveMember.service.query.ExecutiveMemberQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class ExecutiveMemberController {

    private final ExecutiveMemberQueryService executiveMemberQueryService;

    /**
     * 운영진 검색 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberSearchResponse>>> searchMembers(MemberSearchRequest request) {

        List<MemberSearchResult> result = executiveMemberQueryService.searchMembers(MemberSearchCommand.from(request));

        List<MemberSearchResponse> responses = result.stream()
                        .map(MemberSearchResponse::from)
                        .toList();

        return ResponseEntity.ok(ApiResponse.success("운영진 검색 조회에 성공하였습니다.",responses));
    }
}
