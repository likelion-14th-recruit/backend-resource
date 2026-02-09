package org.likelion.recruit.resource.message.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum MessageType {
        VERIFICATION,           // 인증
        DOCUMENT_PASSED,        // 서류 통과
        DOCUMENT_FAILED,        // 서류 탈락
        INTERVIEW_PASSED,       // 면접 통과
        INTERVIEW_FAILED,       // 면접 탈락
    }

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public enum MessageStatus {
        PENDING,                // 대기
        SENT,                   // 전송 완료
        FAILED                  // 전송 실패
    }

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @Column(nullable = false, name = "receiver_phone_number")
    private String receiver;

    // 생성 메서드
    private Message(MessageType messageType, MessageStatus messageStatus, String receiver) {
        this.messageType = messageType;
        this.messageStatus = messageStatus;
        this.receiver = receiver;
    }

    public static Message create(MessageType messageType, String receiver) {
        return new Message(messageType, MessageStatus.PENDING, receiver);
    }

    // 비즈니스 로직
    public void send() {
        if (this.messageStatus != MessageStatus.PENDING) {
            return;
        }
        messageStatus = MessageStatus.SENT;
    }

    public void sendFailed() {
        if (this.messageStatus != MessageStatus.PENDING) {
            return;
        }
        messageStatus = MessageStatus.FAILED;
    }

}
