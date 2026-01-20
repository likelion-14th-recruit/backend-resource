package org.likelion.recruit.resource.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지원서가 존재합니다.");


    private final HttpStatus status;
    private final String message;
}
