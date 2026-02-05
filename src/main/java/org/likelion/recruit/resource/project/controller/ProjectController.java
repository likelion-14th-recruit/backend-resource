package org.likelion.recruit.resource.project.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.common.dto.response.SliceResponse;
import org.likelion.recruit.resource.project.dto.command.ProjectSearchCommand;
import org.likelion.recruit.resource.project.dto.request.ProjectSearchRequest;
import org.likelion.recruit.resource.project.dto.result.ProjectSearchResult;
import org.likelion.recruit.resource.project.service.query.ProjectQueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectQueryService projectQueryService;

    /**
     * 프로젝트 검색 조회하기
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SliceResponse<ProjectSearchResult>>> searchProjects(ProjectSearchRequest request,
                                                                                          @PageableDefault(size = 15) Pageable pageable) {
        Slice<ProjectSearchResult> sliceResult = projectQueryService
                .searchProjects(ProjectSearchCommand.from(request), pageable);
        return ResponseEntity.ok(ApiResponse.success("프로젝트 검색 조회에 성공하였습니다.",
                SliceResponse.from(sliceResult)));
    }
}
