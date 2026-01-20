package org.likelion.recruit.resource.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //solapi
    SMS_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "문자 전송에 실패했습니다."),

    //verification
    VERIFICATION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 인증된 전화번호입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "인증번호가 올바르지 않습니다."),
    VERIFICATION_REQUIRED(HttpStatus.BAD_REQUEST, "전화번호 인증이 필요합니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Verification not found"),

    //application
    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지원서가 존재합니다."),
    APPLICATION_NOT_EXISTS(HttpStatus.NOT_FOUND, "지원서가 존재하지 않습니다."),
    QUESTION_NOT_EXISTS(HttpStatus.NOT_FOUND, "질문이 존재하지 않습니다.");
    private final HttpStatus status;
    private final String message;
}
