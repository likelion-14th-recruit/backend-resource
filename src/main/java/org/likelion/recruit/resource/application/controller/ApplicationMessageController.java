package org.likelion.recruit.resource.application.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.response.SendDocumentResultResponse;
import org.likelion.recruit.resource.application.dto.result.SendDocumentResultDto;
import org.likelion.recruit.resource.application.service.command.ApplicationMessageCommandService;
import org.likelion.recruit.resource.common.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications/messages")
public class ApplicationMessageController {

    private final ApplicationMessageCommandService applicationMessageCommandService;

    @PostMapping("/document-result")
    public ResponseEntity<ApiResponse<SendDocumentResultResponse>> sendDocumentResultMessages(){
        SendDocumentResultDto result = applicationMessageCommandService.sendDocumentResultMessages();
        return ResponseEntity.ok(ApiResponse.success("서류 합불 메시지 전송 완료", SendDocumentResultResponse.from(result)));
    }
}
