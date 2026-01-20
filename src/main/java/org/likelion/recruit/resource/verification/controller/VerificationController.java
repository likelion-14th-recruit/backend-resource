package org.likelion.recruit.resource.verification.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.likelion.recruit.resource.verification.dto.command.VerifyConfirmCommand;
import org.likelion.recruit.resource.verification.dto.command.VerifyPhoneCommand;
import org.likelion.recruit.resource.verification.dto.request.VerifyConfirmRequest;
import org.likelion.recruit.resource.verification.dto.request.VerifyPhoneRequest;
import org.likelion.recruit.resource.verification.service.command.VerificationCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
public class VerificationController {

    private final VerificationCommandService verificationCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> sendVerificationCode(@RequestBody VerifyPhoneRequest req) {
        verificationCommandService.sendVerificationCode(VerifyPhoneCommand.from(req));

        return ResponseEntity.ok(ApiResponse.success("인증번호 발송에 성공하였습니다."));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirmVerificationCode(@RequestBody VerifyConfirmRequest req) {
        verificationCommandService.confirmVerificationCode(VerifyConfirmCommand.from(req));

        return ResponseEntity.ok(ApiResponse.success("전화번호 인증이 완료되었습니다."));
    }
}
