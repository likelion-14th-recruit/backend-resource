package org.likelion.recruit.resource.application.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.request.LoginRequest;
import org.likelion.recruit.resource.application.dto.response.LoginResponse;
import org.likelion.recruit.resource.application.dto.result.LoginResult;
import org.likelion.recruit.resource.application.service.query.ApplicationQueryService;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final ApplicationQueryService applicationQueryService;

    /**
     * 로그인
     */
    @PostMapping
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest req) {
        LoginResult result = applicationQueryService.login(req.getPhoneNumber(), req.getPassword());
        return ResponseEntity.ok(ApiResponse.success("로그인에 성공하였습니다.",
                LoginResponse.from(result)));
    }
}
