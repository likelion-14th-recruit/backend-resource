package org.likelion.recruit.resource.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    VERIFICATION_REQUIRED(HttpStatus.BAD_REQUEST, "전화번호 인증이 필요합니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Verification not found"),
    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지원서가 존재합니다.");


    private final HttpStatus status;
    private final String message;
}
