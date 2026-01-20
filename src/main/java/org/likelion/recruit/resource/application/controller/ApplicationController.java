package org.likelion.recruit.resource.application.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.command.ApplicationCreateCommand;
import org.likelion.recruit.resource.application.dto.request.ApplicationCreateRequest;
import org.likelion.recruit.resource.application.dto.response.PublicIdResponse;
import org.likelion.recruit.resource.application.dto.result.PublicIdResult;
import org.likelion.recruit.resource.application.service.command.ApplicationCommandService;
import org.likelion.recruit.resource.application.service.query.ApplicationQueryService;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationCommandService applicationCommandService;
    private final ApplicationQueryService applicationQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<PublicIdResponse>> createApplication(@Valid @RequestBody ApplicationCreateRequest request){
        String publicId = applicationCommandService.createApplication(ApplicationCreateCommand.from(request));
        PublicIdResult result = PublicIdResult.from(publicId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("지원서 인적사항이 생성되었습니다.", PublicIdResponse.from(result)));
    }
}
