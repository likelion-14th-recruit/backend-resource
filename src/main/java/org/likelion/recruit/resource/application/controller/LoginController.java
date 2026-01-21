package org.likelion.recruit.resource.application.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.request.LoginRequest;
import org.likelion.recruit.resource.application.dto.request.ResetPasswordRequest;
import org.likelion.recruit.resource.application.dto.response.LoginResponse;
import org.likelion.recruit.resource.application.dto.response.PublicIdResponse;
import org.likelion.recruit.resource.application.dto.result.LoginResult;
import org.likelion.recruit.resource.application.dto.result.PublicIdResult;
import org.likelion.recruit.resource.application.service.command.ApplicationCommandService;
import org.likelion.recruit.resource.application.service.query.ApplicationQueryService;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final ApplicationQueryService applicationQueryService;
    private final ApplicationCommandService applicationCommandService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest req) {
        LoginResult result = applicationQueryService.login(req.getPhoneNumber(), req.getPassword());
        return ResponseEntity.ok(ApiResponse.success("로그인에 성공하였습니다.",
                LoginResponse.from(result)));
    }

    /**
     * 비밀번호 새로 생성하기
     */
    @PostMapping("/password/reset")
    public ResponseEntity<ApiResponse<PublicIdResponse>> resetPassword(@RequestBody ResetPasswordRequest req) {
        String result = applicationCommandService.resetPassword(req.getPhoneNumber(), req.getPassword());
        PublicIdResult dto = applicationQueryService.getPublicId(result);

        return ResponseEntity.ok(ApiResponse.success("비밀번호를 재설정하였습니다.",
                PublicIdResponse.from(dto)));
    }
}
