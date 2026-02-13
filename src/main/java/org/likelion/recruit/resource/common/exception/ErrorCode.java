package org.likelion.recruit.resource.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //request
    INVALID_REQUEST_FORMAT(HttpStatus.BAD_REQUEST, "REQUEST 요청이 올바르지 않습니다."),

    //message
    SMS_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "문자 전송에 실패했습니다."),
    DOCUMENT_PASSED_NOT_ASSIGNED_INTERVIEW_SCHEDULE(HttpStatus.INTERNAL_SERVER_ERROR, "서류 합격자 중, 인터뷰 시간을 등록 안한 분이 있습니다."),

    //verification
    VERIFICATION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 인증된 전화번호입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "인증번호가 올바르지 않습니다."),
    VERIFICATION_REQUIRED(HttpStatus.BAD_REQUEST, "전화번호 인증이 필요합니다."),
    VERIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Verification not found"),

    //interview
    INTERVIEW_TIME_NOT_EXISTS(HttpStatus.NOT_FOUND, "인터뷰 시간이 존재하지 않습니다."),
    INTERVIEW_NOT_SCHEDULED(HttpStatus.BAD_REQUEST, "면접 일정이 확정되지 않았습니다."),

    //application
    APPLICATION_ALREADY_SUBMITTED(HttpStatus.UNAUTHORIZED, "이미 제출한 지원서가 있습니다."),
    APPLICATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 지원서가 존재합니다."),
    APPLICATION_NOT_EXISTS(HttpStatus.NOT_FOUND, "지원서가 존재하지 않습니다."),
    QUESTION_NOT_EXISTS(HttpStatus.NOT_FOUND, "질문이 존재하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),
    APPLICATION_INCOMPLETE(HttpStatus.BAD_REQUEST, "모든 필수 질문에 답변해야 제출이 가능합니다."),
    APPLICATION_SUBMISSION_EXPIRED(HttpStatus.BAD_REQUEST, "지원서 제출 기간이 종료되었습니다.");
    private final HttpStatus status;
    private final String message;
}
